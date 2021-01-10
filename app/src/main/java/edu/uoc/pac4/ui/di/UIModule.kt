package edu.uoc.pac4.ui.di

import edu.uoc.pac4.ui.LaunchViewModel
import edu.uoc.pac4.ui.login.oauth.AuthenticationViewModel
import edu.uoc.pac4.ui.profile.ProfileViewModel
import edu.uoc.pac4.ui.streams.StreamsViewModel
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val uiModule = module {

    single { StreamsViewModel(streamsRepository = get()) }
    single { ProfileViewModel(twitchUserRepository = get(), authenticationRepository = get()) }
    single { LaunchViewModel(repository = get()) }
    single { AuthenticationViewModel(authenticationRepository = get()) }

}