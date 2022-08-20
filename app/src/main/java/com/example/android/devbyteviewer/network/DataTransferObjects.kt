package com.example.android.devbyteviewer.network

import com.example.android.devbyteviewer.database.DatabaseVideo
import com.example.android.devbyteviewer.domain.Video

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

/**
 * VideoHolder holds a list of Videos.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "videos": []
 * }
 */
data class NetworkVideoContainer(val videos: List<NetworkVideo>)

/**
 * Videos represent a devbyte that can be played.
 */
data class NetworkVideo(
        val title: String,
        val description: String,
        val url: String,
        val updated: String,
        val thumbnail: String,
        val closedCaptions: String?)

/** Convert Network results to database objects **/
// Add an extension function which converts from database objects to domain objects:
fun NetworkVideoContainer.asDomainModel(): List<Video> {
    return videos.map {
        Video(
                title = it.title,
                description = it.description,
                url = it.url,
                updated = it.updated,
                thumbnail = it.thumbnail)
    }
}
// Create an extension function that converts from data transfer objects to database objects:
fun NetworkVideoContainer.asDatabaseModel(): Array<DatabaseVideo> {
    return videos.map {
        DatabaseVideo(
                title = it.title,
                description = it.description,
                url = it.url,
                updated = it.updated,
                thumbnail = it.thumbnail)
    }.toTypedArray()
}


/**
Let’s take a moment to review. Our app will have three types of data objects.

The first are domain objects, stored in the domain package.
The second are data transfer objects for the network stored in the network package.
The third type of object is a database object. These are different from data transfer objects and domain objects because they’re entirely for interacting with the database.

Go ahead and take a look at the entities we’ve provided for you in your project in Android Studio.

    network/DataTransferObjects.kt

        - class NetworkVideoContainer
        - class NetworkVideo

    domain/Models.kt

        - class Video

Data transfer objects are responsible for parsing and representing values on the network while domain objects are the core data representation for our app.

Open DataTransferObjects.kt and review the helpful extension function we’ve provided that converts from network entities to domain entities:

fun NetworkVideoContainer.asDomainModel(): List<Video> {
return videos.map {
Video (
title = it.title,
description = it.description,
url = it.url,
updated = it.updated,
thumbnail = it.thumbnail)
}
}

The code above defines an extension function on NetworkViewHolder called asDomainModel that returns a List<Video>.
In the body, you can see it just converts NetworkVideo into Video using the map function from the Kotlin standard library.

By making these objects a separate classes we create what’s called a “separation of concerns.” The database object is concerned only with things in the database,
while the data transfer objects are concerned only with things on the network.
 */