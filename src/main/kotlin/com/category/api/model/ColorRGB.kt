package com.category.api.model

enum class ColorRGB private constructor(private val r: Int, private val g: Int, private val b: Int) {

    WHITE(255, 255, 255),
    RED(255, 0, 0),
    BLUE(0, 0, 255),
    PINK(255, 192, 203),
    GREY(128, 128, 128),
    BLACK(0, 0, 0),
    GREEN(0, 255, 0),
    ORANGE(255, 165, 0),
    PURPLE(128, 0, 128),
    YELLOW(255, 255, 0),
    MULTI(55, 175, 220);

    val colorHex: String
        get() = String.format("%02x%02x%02x", r, g, b).toUpperCase()

}
