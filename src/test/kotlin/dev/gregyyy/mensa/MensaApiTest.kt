package dev.gregyyy.mensa

import dev.gregyyy.mensaapi.MensaApi
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MensaApiTest {

    @Test
    fun testGetDaysHtml() {
        val days = MensaApi(MensaApi.Source.HTML).getDays("adenauering")

        assertNotNull(days)
        assertTrue { days.isNotEmpty() }
    }

    @Disabled("This test is disabled because the API has authentication now.")
    @Test
    fun testGetDaysJson() {
        val days = MensaApi(MensaApi.Source.JSON).getDays("adenauering")

        assertNotNull(days)
        assertTrue { days.isNotEmpty() }
    }

}