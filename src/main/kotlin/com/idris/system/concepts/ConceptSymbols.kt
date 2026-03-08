package com.idris.system.concepts

// The specific strings used to represent each concept in the CLI


object ConceptSymbols {
    const val FOUNDATION = "f"
    const val CHALLENGE = "c"
    const val EXAM = "e"
    const val PROGRESSION = "p"
    const val DAY = "d"
    const val EXPERIMENT = "x"
    const val RECORD = "r"
    const val TILE = "t"

    val all = listOf(FOUNDATION, CHALLENGE, EXAM,
                     PROGRESSION, DAY, EXPERIMENT, RECORD, TILE)
}