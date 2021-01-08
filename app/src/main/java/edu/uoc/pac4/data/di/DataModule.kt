package edu.uoc.pac4.data.di

import edu.uoc.pac4.data.network.Network
import edu.uoc.pac4.data.streams.StreamDataSource
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.streams.TwitchStreamsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val dataModule = module {
    // TODO: Init your Data Dependencies

    // Streams example

    single<StreamsRepository> { TwitchStreamsRepository(StreamDataSource(Network.createHttpClient(androidContext()))) }
}