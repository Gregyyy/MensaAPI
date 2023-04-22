package dev.gregyyy.mensaapi

import dev.gregyyy.mensaapi.html.MensaHtmlSource
import dev.gregyyy.mensaapi.json.MensaJsonSource

class MensaApi(private val source: Source = Source.HTML) {

    fun getDays(mensa: String): List<MensaDay> {
        return source.apiSource.getDays(mensa)
    }

    enum class Source(val apiSource: MensaSource) {
        JSON(MensaJsonSource()),
        HTML(MensaHtmlSource());
    }

}