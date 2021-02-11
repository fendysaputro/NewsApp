package id.phephen.newsapp.response

import com.google.gson.annotations.SerializedName

/**
 * Created by phephen on 11,February,2021
 * https://github.com/fendysaputro
 */
data class SourcesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("sources") val sources: List<Sources>
)

data class Sources(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val desc: String,
    @SerializedName("url") val url: String
)
