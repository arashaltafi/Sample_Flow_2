package com.arash.altafi.sampleflow2.data.remote

import com.arash.altafi.sampleflow2.model.DogResponse
import com.arash.altafi.sampleflow2.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DogService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}
