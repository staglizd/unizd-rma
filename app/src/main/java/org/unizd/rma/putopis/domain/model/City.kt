package org.unizd.rma.putopis.domain.model

/** Grad s trenutnom temperaturom. Sve je `val`: model je nepromjenjiv. */
data class City(
    val id: Int,
    val name: String,
    val region: String,
    val temp: Int,
)
