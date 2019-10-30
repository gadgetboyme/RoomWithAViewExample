package com.gadgetboyme.roomwordsample.repository.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Each @Entity class represents a table. Annotate your class declaration to indicate that it's an entity.
@Entity(tableName = "word_table")

//Every entity needs a primary key. To keep things simple, each word acts as its own primary key.
//Specify the name of the column in the table if you want it to be different from the name of the member variable.
class Word (@PrimaryKey @ColumnInfo(name = "word") val word: String)

//Every field that's stored in the database needs to be either public or have a "getter" method.