package com.example.android.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.launch

/** @param application
 The application that this viewmodel is attached to, it's safe to hold a reference to applications across rotation
 since Application is never recreated during activity or fragment lifecycle events.
 */
class VM_DevByte(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application.baseContext)
    private val videosRepository = VideosRepository(database)

    val playlist: LiveData<List<Video>>
    get() = videosRepository.videos

    init {
        viewModelScope.launch {
            videosRepository.refreshVideos()
        }
    }

    /*
    private var _playlist = MutableLiveData<List<Video>>(emptyList())
    val playlist: LiveData<List<Video>>
    get() = _playlist

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _playlist.postValue(videosRepository.videos.value)
                videosRepository.refreshVideos()
            }catch (e : Exception) {
                Timber.w("Something wrong happens")
            }

        }
    }
     */


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VM_DevByte::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return VM_DevByte(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
