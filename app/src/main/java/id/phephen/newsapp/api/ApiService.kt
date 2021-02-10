package id.phephen.newsapp.api

import id.phephen.newsapp.response.CountryCheckResponse
import id.phephen.newsapp.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by phephen on 08,February,2021
 * https://github.com/fendysaputro
 */
interface ApiService {

    @GET
    suspend fun getIpInfo(@Url url: String): Response<CountryCheckResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String
    ): Response<NewsResponse>
}