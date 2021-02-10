package id.phephen.newsapp.repository

import android.content.Context
import id.phephen.newsapp.data.DataSource
import id.phephen.newsapp.helper.nonEmptyStringOrNull
import id.phephen.newsapp.response.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge

/**
 * Created by phephen on 09,February,2021
 * https://github.com/fendysaputro
 */
class DataRepository (context: Context) {
    private val dataSource: DataSource

    init {
        dataSource = DataSource(context)
    }

    suspend fun getNews(query: String?): Flow<NewsResponse> {
        return query?.nonEmptyStringOrNull()?.let { q ->
            dataSource.getNews(q)
        } ?: dataSource.getCountryCode()
            .flatMapMerge { countryCode ->
                dataSource.getTopHeadlines(countryCode)
            }
    }
}