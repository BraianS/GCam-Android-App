package dev.braian.gcamxmlhub.core

typealias CredentialResponse = ApiResult<String>
typealias FirebaseSignInResponse = ApiResult<Boolean>

sealed class ApiResult<out T > {
   data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    object Loading: ApiResult<Nothing>()

/*    val extractData: T?
        get() = when (this) {
            is Success -> data
            is Error -> null
            is Loading -> null
        }*/
}