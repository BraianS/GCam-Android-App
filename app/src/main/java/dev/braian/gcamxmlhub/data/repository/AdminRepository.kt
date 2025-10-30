package dev.braian.gcamxmlhub.data.repository

import dev.braian.gcamxmlhub.core.ApiResult
import dev.braian.gcamxmlhub.data.model.User

interface AdminRepository {

    suspend fun addUser(user: User): ApiResult<Boolean>
    suspend fun updateUser(user: User): ApiResult<Boolean>
    suspend fun deleteUser(userId: String): ApiResult<Boolean>
    suspend fun getUser(userId: String): ApiResult<User>
    suspend fun getAllUsers(): ApiResult<List<User>>
    fun sendXmlFile(xmlFile: ByteArray): ApiResult<Boolean>

}