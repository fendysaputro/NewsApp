package id.phephen.newsapp.helper

/**
 * Created by phephen on 09,February,2021
 * https://github.com/fendysaputro
 */

data class NetworkException(
    val errorCode: Int,
    val url: String?,
    val errorMessage: String?
) : Throwable(errorMessage)
