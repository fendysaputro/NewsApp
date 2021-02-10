package id.phephen.newsapp.response

import com.google.gson.annotations.SerializedName

/**
 * Created by phephen on 09,February,2021
 * https://github.com/fendysaputro
 */
data class CountryCheckResponse(
    @SerializedName("status") val status: String,
    @SerializedName("country") val country: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("region") val region: String,
    @SerializedName("regionName") val regionName: String,
    @SerializedName("city") val city: String,
    @SerializedName("zip") val zip: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("isp") val isp: String,
    @SerializedName("org") val org: String,
    @SerializedName("query") val query: String
)