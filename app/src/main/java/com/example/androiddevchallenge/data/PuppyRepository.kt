package com.example.androiddevchallenge.data

import android.content.Context
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.Buffer
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

/**
 * @author Kingsley Adio
 * @since 01 Mar, 2021
 */
class PuppyRepository(private val context: Context, private val moshi: Moshi) {

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun getPuppies(): List<Puppy> {
        // Do better
        return withContext(Dispatchers.IO) {
            context.assets.open("data/puppies.json").let { ins ->
                val buffer = Buffer()
                buffer.readFrom(ins)
                moshi.adapter<List<Puppy>>(typeOf<List<Puppy>>().javaType)
                    .fromJson(buffer)
                    .orEmpty()
            }
        }
    }
}
