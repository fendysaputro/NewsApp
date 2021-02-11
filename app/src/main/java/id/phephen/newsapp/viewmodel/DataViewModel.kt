package id.phephen.newsapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.phephen.newsapp.helper.ResultWrapper
import id.phephen.newsapp.helper.failure
import id.phephen.newsapp.helper.loading
import id.phephen.newsapp.helper.success
import id.phephen.newsapp.repository.DataRepository
import id.phephen.newsapp.response.NewsResponse
import id.phephen.newsapp.response.SourcesResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by phephen on 09,February,2021
 * https://github.com/fendysaputro
 */
class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DataRepository(application)
    val newsLiveData = MutableLiveData<ResultWrapper<NewsResponse, Throwable>>()
    val sourcesLiveData = MutableLiveData<ResultWrapper<SourcesResponse, Throwable>>()

    private var apiJob: Job? = null

    fun fetchNewsData(query: String? = null) {
        apiJob?.cancel()
        apiJob = viewModelScope.launch() {
            newsLiveData.loading()
            repo.getNews(query)
                .catch { error ->
                    newsLiveData.failure(error)
                }
                .collect { newsLiveData.success(it) }
        }
    }

    fun fetchSourcesData() {
        apiJob?.cancel()
        apiJob = viewModelScope.launch {
            sourcesLiveData.loading()
            repo.getSources()
                .catch { error ->
                    sourcesLiveData.failure(error)
                }
                .collect { sourcesLiveData.success(it) }
        }
    }
}