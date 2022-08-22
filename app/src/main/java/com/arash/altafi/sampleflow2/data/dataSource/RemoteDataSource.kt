package com.arash.altafi.sampleflow2.data.dataSource

import com.arash.altafi.sampleflow2.data.remote.DogService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val dogService: DogService) {

    suspend fun getDog() =
        dogService.getDog()

}