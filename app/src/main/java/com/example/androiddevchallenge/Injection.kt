package com.example.androiddevchallenge

import android.content.Context
import com.example.androiddevchallenge.data.PuppyRepository
import com.squareup.moshi.Moshi

/**
 * @author Kingsley Adio
 * @since 01 Mar, 2021
 */
object Injection {

    private lateinit var appContext: Context

    fun init(appContext: Context) {
        this.appContext = appContext
    }

    val moshi: Moshi by lazy(LazyThreadSafetyMode.NONE) {
        Moshi.Builder().build()
    }

    val puppyRepository: PuppyRepository by lazy {
        PuppyRepository(appContext, moshi)
    }
}
