package dev.gregyyy.mensa.json

import dev.gregyyy.mensaapi.json.MensaJsonSource
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@Disabled("This test is disabled because the API has authentication now.")
class MensaJsonSourceTest {

    @Test
    fun getApiMensa() {
        val apiMensa = MensaJsonSource().getApiMensa("adenauering")

        assertNotNull(apiMensa)
        assertTrue { apiMensa.mensa.isNotEmpty() }
    }

    @Test
    fun testGetDays() {
        val days = MensaJsonSource().getDays("adenauering")

        assertTrue { days.isNotEmpty() }
    }

}