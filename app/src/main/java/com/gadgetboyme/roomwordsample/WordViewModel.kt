package com.gadgetboyme.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gadgetboyme.roomwordsample.repository.Database.WordRoomDatabase
import com.gadgetboyme.roomwordsample.repository.Model.Word
import com.gadgetboyme.roomwordsample.repository.Repository.WordRepository
import kotlinx.coroutines.launch

//Class extends AndroidViewModel and requires application as a parameter
class WordViewModel(application: Application) : AndroidViewModel(application){

    //The ViewModel maintains a reference to the repository to get data.
    private val repository: WordRepository

    //LiveData gives us updated words when they change (i.e. a cached list of words)
    val allWords: LiveData<List<Word>>

    //Class initialiser, used to initialise repository and allWords
    init{
        //Gets a reference to WordDao from WordRoomDatabase to construct the correct WordRepository
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        //Construct the repository based on the WordDao
        repository = WordRepository(wordsDao)

        //Initialise the allWords LiveData using the repository constructed on the line above.
        allWords = repository.allWords
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on the main thread,
     * blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope, which will be used here.
     */
    fun insert(word: Word) = viewModelScope.launch{
        repository.insert(word)
    }

}