package androidearly.heroApp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("85eddff613c51a369855c0d31848b3f3/search/{name}")
    suspend fun getHeroes(@Path("name") superHeroName: String): Response<SuperHeroDataResponse>

    @GET("85eddff613c51a369855c0d31848b3f3/{id}")
    suspend fun getHeroesID(@Path("id") superID: String): Response<SuperHeroDetailResponse>
}
