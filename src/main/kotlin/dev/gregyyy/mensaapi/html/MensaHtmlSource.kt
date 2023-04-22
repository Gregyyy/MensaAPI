package dev.gregyyy.mensaapi.html

import dev.gregyyy.mensaapi.*
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MensaHtmlSource: MensaSource {

    companion object {

        private const val HOST = "https://www.sw-ka.de/de/hochschulgastronomie/speiseplan/mensa_adenauerring/"

    }

    override fun getDays(mensa: String): List<MensaDay> {
        val days = getDisplayedDays()
        val mensaDays = mutableListOf<MensaDay>()

        for ((i, day) in days.withIndex()) {
            mensaDays.add(scrape(i + 1, day))
        }

        return mensaDays
    }

    /**
     * Returns the days that are displayed on the website. This is usually the current day and the next 4 days.
     * This is represented by the number from 1 to 5.
     */
    fun getDisplayedDays(): List<LocalDate> {
        val doc = Jsoup.connect(HOST).get()
        val body = doc.body()

        val days = mutableListOf<LocalDate>()
        for (day in body.select(".canteen-day-nav").get(0).children()) {
            val dayName = day.select("a > span").text().split(" ")[1]
            val dayDate = LocalDate.parse(dayName + LocalDate.now().year,
                DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            days.add(dayDate)
        }

        return days
    }

    fun scrape(day: Int, date: LocalDate): MensaDay {
        if (day < 1 || day > 5)
            throw IllegalArgumentException("Day must be between 1 and 5")

        val doc = Jsoup.connect(HOST).get()
        val body = doc.body()

        val day1 = body.select("div[id='canteen_day_$day'] > table > tbody")[0]

        val lines = mutableListOf<Line>()
        for (row in day1.select(".mensatype_rows")) {
            val line = row.select(".mensatype").text()

            val dishes = mutableListOf<Dish>()
            for (dish in row.select(".meal-detail-table > tbody").get(0).children()) {
                if (dish.className().isEmpty())
                    continue

                val meal = dish.select(".menu-title > span > b").text()
                val description = dish.select(".menu-title > span > span").text()
                try {
                    val priceStudents = parsePrice(dish.select(".price_1").text())
                    val priceGuests = parsePrice(dish.select(".price_2").text())
                    val priceEmployees = parsePrice(dish.select(".price_3").text())
                    val pricePupils = parsePrice(dish.select(".price_4").text())

                    dishes.add(Dish(meal, description, priceStudents, priceGuests, priceEmployees, pricePupils))
                } catch (e: Exception) {
                    throw java.lang.Exception("Error parsing price for '$meal ($description)' on '$line'", e)
                }
            }

            lines.add(Line(LineName.getByWebsiteName(line), dishes))
        }

        return MensaDay(date, lines)
    }

    private fun parsePrice(price: String): Double {
        if (price.isEmpty())
            return 0.0

        return price.replace(",", ".").split(" ")[0].toDouble()
    }

}