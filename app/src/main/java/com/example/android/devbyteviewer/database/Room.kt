/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)       // As you can't add new data with existed PrimaryKey, so just replace the whole new data with the new one.
    fun insertAll(vararg videos: DatabaseVideo)            // and this type of insert is called an upsert (update + insert).
} //Error: why ' vararg ' keyword before a variable in parameters means it's almost a list. ????

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase : RoomDatabase() {
    abstract val videoDao: VideoDao
}

@Volatile	// It means that it will never be cached, and it will executes in the main memory (No intersection calls).
private lateinit var INSTANCE: VideosDatabase       //Singleton pattern

fun getDatabase(context: Context): VideosDatabase
{
    synchronized(VideosDatabase::class.java)
    {
        if (!::INSTANCE.isInitialized) {        // By using kotlin function [ isInitialized ] for lateinit variables.
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                VideosDatabase::class.java,
                "videos")
                .build()
        }
    }
    return INSTANCE
}
