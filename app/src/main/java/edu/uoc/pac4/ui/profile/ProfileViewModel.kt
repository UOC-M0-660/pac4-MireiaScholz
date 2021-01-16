package edu.uoc.pac4.ui.profile

import androidx.lifecycle.ViewModel
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UserRepository

class ProfileViewModel(
    private val twitchUserRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    suspend fun getUser(): User? {
        return twitchUserRepository.getUser()
    }

    suspend fun updateUser(description: String): User? {
        return twitchUserRepository.updateUser(description)
    }

    fun logout() {
        authenticationRepository.logout()
    }
}