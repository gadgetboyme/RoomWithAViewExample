package com.gadgetboyme.roomwordsample.repository.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gadgetboyme.roomwordsample.repository.Model.Word

//WordDao is an interface, since DAOs must either be interfaces or abstract.

//The @Dao annotation identifies it as a DAO class for Room.
@Dao
interface WordDao {
    //Returns a list of words sorted in ascending order.
    @Query("SELECT * from word_table ORDER BY word ASC")
    // A method to get all the words and have it return a List of Words.
    //Making the return value of type LiveData<T>, the observable object can execute code when this value changes. Room provides for this automatically
    fun getAlphabetizedWords(): LiveData<List<Word>>

    //Declares a suspend function to insert one word.
    //The @Insert annotation is a special DAO function annotation where you don't have to provide any SQL
    //The conflict strategy ignores a new word if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    //There is no convenience annotation for deleting multiple entities, so it's annotated with the generic @Query.
    //Declares a suspend function to delete all the words.
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}