package ru.modulkassa.findgoods.domain.good

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import io.reactivex.BackpressureStrategy.BUFFER
import io.reactivex.Flowable
import ru.modulkassa.findgoods.domain.network.dto.Command
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CatalogJsonStream @Inject constructor(
    private val gson: Gson
) {
    fun toFlowable(stream: InputStream): Flowable<Command> {
        return Flowable.create({ emitter ->
            try {
                JsonReader(InputStreamReader(stream, Charsets.UTF_8)).use { jsonReader ->
                    jsonReader.beginArray()

                    while (jsonReader.hasNext()) {
                        val commandDto: Command = gson.fromJson(jsonReader,
                            Command::class.java)
                        emitter.onNext(commandDto)
                    }

                    jsonReader.endArray()
                    emitter.onComplete()
                }
            } catch (e: IOException) {
                emitter.onError(e)
            }
        }, BUFFER)
    }
}