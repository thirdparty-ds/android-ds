package com.redrield.androidds.ds

enum class Colour {
    Red,
    Blue
}

class Alliance(colour: Colour, position: Int) {
    val ptr: Long

    init {
        if(position !in 1..3) {
            throw IllegalArgumentException("Position must be in range [1, 3]")
        }

        ptr = when(colour) {
            Colour.Red -> createRed(position)
            Colour.Blue -> createBlue(position)
        }
    }

    private external fun createRed(n: Int): Long
    private external fun createBlue(n: Int): Long
}
