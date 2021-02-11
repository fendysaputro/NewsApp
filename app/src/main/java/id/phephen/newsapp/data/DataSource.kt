package id.phephen.newsapp.data

import android.content.Context
import id.phephen.newsapp.api.RetrofitClient
import id.phephen.newsapp.helper.Constant
import id.phephen.newsapp.helper.NetworkException
import id.phephen.newsapp.helper.nonEmptyStringOrNull
import id.phephen.newsapp.response.NewsResponse
import id.phephen.newsapp.response.SourcesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * Created by phephen on 09,February,2021
 * https://github.com/fendysaputro
 */
class DataSource(context: Context) {
    private val API_KEY = Constant.apiKey

    private val DEFAULT_COUNTRY = "id"

    suspend fun getCountryCode(): Flow<String> {
        return flow {
            val response = RetrofitClient.api.getIpInfo(Constant.COUNTY_URL)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it.countryCode.toLowerCase())
                    return@flow
                }
            }
            emit(DEFAULT_COUNTRY)
        }.catch { emit(DEFAULT_COUNTRY) }
    }

    suspend fun getTopHeadlines(country: String): Flow<NewsResponse> {
        return flow {
            val response = RetrofitClient.api.getTopHeadlines(API_KEY, country)

            checkStatus(response, this)
        }
    }

    suspend fun getNews(
            query: String
    ): Flow<NewsResponse> {
        return flow {
            val response = RetrofitClient.api.getNews(
                    API_KEY,
                    query
            )

            checkStatus(response, this)
        }
    }

    suspend fun getSources(query: String): Flow<SourcesResponse> {
        return flow {
            val response = RetrofitClient.api.getSources(API_KEY, query)

            checkStatus(response, this)
        }
    }

    private suspend fun <T> checkStatus(response: Response<T>, flowCollector: FlowCollector<T>) {
        if (response.isSuccessful) {
            response.body()?.let {
                flowCollector.emit(it)
                return
            }
        }

        val errorMessage =
                response.errorBody()?.string()?.nonEmptyStringOrNull()
                        ?: "Something went wrong."

        throw NetworkException(
                response.code(),
                response.raw().request.url.toString(),
                errorMessage
        )
    }
}