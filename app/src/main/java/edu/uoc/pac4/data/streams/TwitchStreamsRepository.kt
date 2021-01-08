package edu.uoc.pac4.data.streams

import android.util.Log

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
    private val streamDataSource: StreamDataSource
) : StreamsRepository {

    override suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>> {

        streamDataSource.getStreams(cursor)?.let { response ->
            Log.d("StreamsActivity", "Got Streams: $response")

            val streams = response.data.orEmpty()
            val nextCursor = response.pagination?.cursor

            return Pair(nextCursor, streams)
        }

        return Pair(null, listOf())
    }

}