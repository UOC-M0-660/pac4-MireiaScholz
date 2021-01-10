package edu.uoc.pac4.ui.login.oauth

import androidx.lifecycle.ViewModel
import edu.uoc.pac4.data.oauth.AuthenticationRepository

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {
    suspend fun login(authorizationCode: String): Boolean {
        return authenticationRepository.login(authorizationCode)
    }

}