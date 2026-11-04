package org.unizd.rma.putopis.ui

/** Grad s trenutnom temperaturom. Sve je `val`: model je nepromjenjiv. */
data class City(
    val id: Int,
    val name: String,
    val region: String,
    val temp: Int,
)

/** Probni podaci; u V4 ih zamjenjuju pravi podaci s interneta. */
val sampleCities = listOf(
    City(1, "Zadar", "Zadarska", 21),
    City(2, "Split", "Splitsko-dalmatinska", 24),
    City(3, "Rijeka", "Primorsko-goranska", 17),
    City(4, "Osijek", "Osječko-baranjska", 14),
    City(5, "Dubrovnik", "Dubrovačko-neretvanska", 25),
)