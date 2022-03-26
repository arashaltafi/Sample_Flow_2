package com.arash.altafi.sampleflow2.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val dogService: DogService) {

    suspend fun getDog() =
        dogService.getDog()

}