package edu.uoc.pac4.ui.streams

import androidx.lifecycle.ViewModel
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository

class StreamsViewModel(
    private val streamsRepository: StreamsRepository
): ViewModel() {

    suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>> {
        return streamsRepository.getStreams(cursor)
    }
}