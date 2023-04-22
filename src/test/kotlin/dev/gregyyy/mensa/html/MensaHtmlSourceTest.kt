package dev.gregyyy.mensa.html

import dev.gregyyy.mensaapi.html.MensaHtmlSource
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertNotNull

class MensaHtmlSourceTest {

    @Test
    fun testScrape() {
        val day = MensaHtmlSource().scrape(1, LocalDate.now())

        assertNotNull(day)
        print(day)
    }

    @Test
    fun testGetDisplayedDays() {
        val days = MensaHtmlSource().getDisplayedDays()

        assertNotNull(days)
        print(days)
    }

}