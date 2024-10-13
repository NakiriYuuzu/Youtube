package core.data.source.remote

import core.data.source.YoutubeSource

class YoutubeRemoteSource(
    private val service: YoutubeService
) : YoutubeSource.Remote {
    override suspend fun getUsers() = service.getUsers()
}