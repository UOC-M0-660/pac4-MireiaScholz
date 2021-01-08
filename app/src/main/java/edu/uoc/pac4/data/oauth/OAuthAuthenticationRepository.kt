package edu.uoc.pac4.data.oauth

import android.util.Log
import edu.uoc.pac4.data.SessionManager

/**
 * Created by alex on 11/21/20.
 */
class OAuthAuthenticationRepository(
    private val oAuthTokenDataSource: OAuthDataSource,
    private val sessionManager: SessionManager
) : AuthenticationRepository {

    override fun isUserAvailable(): Boolean {
        return sessionManager.isUserAvailable()
    }

    override suspend fun login(authorizationCode: String): Boolean {
        // Launch get Tokens Request
        val tokens: OAuthTokensResponse? = oAuthTokenDataSource.getTokens(authorizationCode)
        tokens?.let { response ->
            Log.d("OAuthRepository", "Got Access token ${response.accessToken}")

            // Save access token and refresh token using the SessionManager class
            sessionManager.saveAccessToken(response.accessToken)
            response.refreshToken?.let {
                sessionManager.saveRefreshToken(it)
            }
            return true
        } ?: run {
            return false
        }
    }

    override fun logout() {
        sessionManager.clearAccessToken()
        sessionManager.clearRefreshToken()
    }
}