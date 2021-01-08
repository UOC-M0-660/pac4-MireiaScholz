package edu.uoc.pac4.data

import android.util.Log
import edu.uoc.pac4.data.network.Endpoints
import edu.uoc.pac4.data.oauth.OAuthConstants
import edu.uoc.pac4.data.oauth.OAuthTokensResponse
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.streams.StreamsResponse
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UsersResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

/**
 * Created by alex on 24/10/2020.
 */

@Deprecated("Refactor with Repository + DataSources")
class TwitchApiService(private val httpClient: HttpClient) {
    private val TAG = "TwitchApiService"

    /// Gets Access and Refresh Tokens on Twitch
    suspend fun getTokens(authorizationCode: String): OAuthTokensResponse? {
        // Get Tokens from Twitch
        try {
            val response = httpClient
                .post<OAuthTokensResponse>(Endpoints.tokenUrl) {
                    parameter("client_id", OAuthConstants.clientID)
                    parameter("client_secret", OAuthConstants.clientSecret)
                    parameter("code", authorizationCode)
                    parameter("grant_type", "authorization_code")
                    parameter("redirect_uri", OAuthConstants.redirectUri)
                }

            return response

        } catch (t: Throwable) {
            Log.w(TAG, "Error Getting Access token", t)
            return null
        }
    }


}