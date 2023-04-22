package dev.gregyyy.mensaapi.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dev.gregyyy.mensaapi.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit


class MensaJsonSource: MensaSource {

    companion object {

        private const val API_HOST = "https://www.sw-ka.de/json_interface"

        private val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        private val mapper = ObjectMapper().registerKotlinModule()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    }

    fun getApiMensa(mensa: String): ApiMensa {
        val url = "$API_HOST/canteen/?mensa[]=$mensa"
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string()

        return mapper.readValue(body, ApiMensa::class.java)
    }

    override fun getDays(mensa: String): List<MensaDay> {
        val apiResponse = getApiMensa(mensa)

        val list = mutableListOf<MensaDay>()

        for (mensaEntry in apiResponse.mensa) {
            if (mensaEntry.key != mensa) {
                continue
            }

            for (dayEntry in mensaEntry.value) {
                val date = Instant.ofEpochMilli(dayEntry.key.toLong() * 1000)
                    .atZone(ZoneId.of("Europe/Berlin")).toLocalDate()

                val lines = mutableListOf<Line>()

                for (lineEntry in dayEntry.value) {
                    val lineName = LineName.getByInternalName(lineEntry.key)

                    val dishes = mutableListOf<Dish>()

                    lineEntry.value.forEach { apiDish ->
                        if (apiDish.meal != null) {
                            dishes.add(
                                Dish(apiDish.meal, apiDish.dish!!, apiDish.price1!!, apiDish.price3!!,
                                    apiDish.price4!!, apiDish.price2!!)
                            )
                        }
                    }

                    lines.add(Line(lineName, dishes))
                }

                list.add(MensaDay(date, lines))
            }
        }


        return list
    }

}