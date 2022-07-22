package com.dobrowins.extremelyinconvenientmessenger

import com.dobrowins.extremelyinconvenientmessenger.data.OneTymeRemoteRepository
import com.dobrowins.extremelyinconvenientmessenger.data.OneTymeRemoteRepositoryImpl
import com.dobrowins.extremelyinconvenientmessenger.data.network.create_note.CreateNoteApi
import com.dobrowins.extremelyinconvenientmessenger.domain.create_note.CreateNote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependenciesGraph {

    object Network {

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder().run {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                        .apply { level = HttpLoggingInterceptor.Level.BODY }
                    addInterceptor(loggingInterceptor)
                }
                build()
            }
        }

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private val createNoteApi: CreateNoteApi by lazy {
            retrofit.create(CreateNoteApi::class.java)
        }

        val oneTymeRemoteRepository: OneTymeRemoteRepository by lazy {
            OneTymeRemoteRepositoryImpl(createNoteApi)
        }
    }

    object Domain {

        val createNote: CreateNote by lazy {
            CreateNote(Network.oneTymeRemoteRepository)
        }
    }
}