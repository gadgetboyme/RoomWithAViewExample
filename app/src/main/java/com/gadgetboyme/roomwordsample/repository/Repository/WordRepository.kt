package com.gadgetboyme.roomwordsample.repository.Repository

import androidx.lifecycle.LiveData
import com.gadgetboyme.roomwordsample.repository.DAO.WordDao
import com.gadgetboyme.roomwordsample.repository.Model.Word

//Declares the DAO as a private property in the constructor. Pass in the DAO
//instead of the whole database, because you only need to access the DAO.
//There's no need to expose the entire database to the repository.
class WordRepository(private val wordDao: WordDao){

    //This is a public property.
    //It's initialized by getting the LiveData list of words from Room; we can do this because of how we defined the getAlphabetizedWords method to return LiveData in the "The LiveData class" step.
    //Room executes all queries on a separate thread.
    //Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    //The suspend modifier tells the compiler that this needs to be called from a coroutine or another suspending function.
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }

}