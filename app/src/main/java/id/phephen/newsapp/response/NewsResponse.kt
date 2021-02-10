package id.phephen.newsapp.response

import com.google.gson.annotations.SerializedName

/**
 * Created by phephen on 08,February,2021
 * https://github.com/fendysaputro
 */
data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Articles>
)


data class Articles(
    @SerializedName("source") val source: Source,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
)

data class Source(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)