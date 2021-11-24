package com.example.marvel.api

import com.example.marvel.Model.Model
import com.example.marvel.key.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable
import retrofit2.http.Path
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

interface Api {
    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int? = 0,
                      @Query("ts") ts: String,
                      @Query("apikey") apiKey: String,
                      @Query("hash") hash: String,
    ): Observable<Model.CharacterResponse>

    @GET("characters/{id}")
     fun getCharacterDetail(@Path("id") id: String,
                            @Query("ts") ts: String,
                            @Query("apikey") apiKey: String,
                            @Query("hash") hash: String)
            : Observable<Model.CharacterResponse>


    @GET("characters/{id}/comics")
     fun getCharacterComics(@Path("id") id: String,
                            @Query("startYear") startYear: Int = 2005,
                            @Query("orderBy") orderBy: String="onsaleDate",
                            @Query("limit") limit: Int = 10,
                            @Query("ts") ts: String,
                            @Query("apikey") apiKey: String,
                            @Query("hash") hash: String)
            : Observable<Model.DetailResponse>


    companion object {
        fun create() : Api {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = okhttp3.OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val gson = GsonBuilder().setLenient().create()

            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
            return retrofit.create<Api>(Api::class.java)
        }
    }

}