package dev.gregyyy.mensaapi

interface MensaSource {
    fun getDays(mensa: String): List<MensaDay>
}