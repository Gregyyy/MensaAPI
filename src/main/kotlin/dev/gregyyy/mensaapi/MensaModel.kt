package dev.gregyyy.mensaapi

import java.lang.IllegalArgumentException
import java.time.LocalDate

data class Dish(
    val name: String,
    val subtext: String,
    val priceStudent: Double,
    val priceWorker: Double,
    val pricePupil: Double,
    val priceGuest: Double
)

data class MensaDay(
    val date: LocalDate,
    val lines: List<Line>
)

data class Line(
    val name: LineName,
    val dishes: List<Dish>
)

enum class LineName(val internalName: String, val displayName: String, val websiteName: String) {
    LINE_1("l1", "Linie 1", "Linie 1 Gut & Günstig"),
    LINE_2("l2", "Linie 2", "Linie 2 Vegane Linie"),
    LINE_3("l3", "Linie 3", "Linie 3"),
    LINE_4("l45", "Linie 4", "Linie 4"),
    LINE_5("l5", "Linie 5", "Linie 5"),
    SCHNITZELBAR("schnitzelbar", "Schnitzel-/Burgerbar", "Schnitzel-/ Burgerbar"),
    LINE_6("update", "Linie 6", "Linie 6"),
    LATE_DISTRIBUTION("abend", "Spätausgabe", "Spätausgabe und Abendessen"),
    KOERIWERK("aktion", "[kœeri]werk", "[kœri]werk 11-14 Uhr"),
    CAFETERIA("heisstheke", "Cafeteria", "Cafeteria 11-14 Uhr"),
    PIZZAWERK("pizza", "[pizza]werk Pizza", "[pizza]werk Pizza 11-14 Uhr"),
    PASTA("pasta", "[pizza]werk Pasta", "[pizza]werk Pasta"),
    SALAT_DESERT("salat_dessert", "[pizza]werk Salate/Vorspeisen", "[pizza]werk Salate / Vorspeisen");

    companion object {
        fun getByInternalName(name: String): LineName {
            for (line in values()) {
                if (line.internalName == name) {
                    return line
                }
            }
            throw IllegalArgumentException("Line with internal name '$name' not found")
        }

        fun getByWebsiteName(name: String): LineName {
            for (line in values()) {
                if (line.websiteName == name) {
                    return line
                }
            }
            throw IllegalArgumentException("Line with website name '$name' not found")
        }
    }
}