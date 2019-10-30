package com.gadgetboyme.roomwordsample.repository.Database

import android.content.Context
import android.support.v4.app.INotificationSideChannel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gadgetboyme.roomwordsample.repository.DAO.WordDao
import com.gadgetboyme.roomwordsample.repository.Model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Annotates class to be a Room Database with a table (entity) of the Word class
//We annotate the class to be a Room database with @Database and declare the entities that belong in the database and set the version number. Listing the entities creates tables in the database.
@Database(entities = arrayOf(Word::class), version = 1)

//The database class for Room must be abstract and extend RoomDatabase
public abstract class WordRoomDatabase : RoomDatabase(){

    //The database provides the DAOs that work with the database. To do that, you create an abstract "getter" method for each @Dao.
    abstract fun wordDao(): WordDao

    //To delete all content and repopulate the database whenever the app is started, create a RoomDatabase.Callback and override onOpen().
    // Because Room database operations cannot be done on the UI thread, onOpen() launches a coroutine on the IO Dispatcher.
    //"e create a custom implementation of the RoomDatabase.Callback(), that also gets a CoroutineScope as constructor parameter. Then, we override the onOpen method to populate the database.
    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let{database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }


        suspend fun populateDatabase(wordDao: WordDao){
            //Delete all content here
            wordDao.deleteAll()

            //Add sample words
            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert((word))

            word = Word("I'm")
            wordDao.insert((word))
            word = Word("HAL 2000")
            wordDao.insert((word))
        }
    }

    companion object{
        //Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        //We've defined a singleton, WordRoomDatabase, to prevent having multiple instances of the database opened at the same time.
        private var INSTANCE: WordRoomDatabase? = null

        //getDatabase returns the singleton. It'll create the database the first time it's accessed, using Room's database builder to create a RoomDatabase object in the application context from the WordRoomDatabase class and names it "word_database".
        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,WordRoomDatabase::class.java, "word_database").addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}

