package com.idris

import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.round

// Implementing Elo rating


fun main() {
    println("Testing Elo...\n")
    val et = EloTool()
    val ratingX = 1500.0
    val ratingY = 1700.0
    val eX = et.expectedOutcome(ratingX, ratingY)  // expected outcome for player X
    val eY = et.expectedOutcome(ratingY, ratingX)  // expected outcome for player Y

    println("Player X (${et.roundToInt(ratingX)}) challenges Player Y (${et.roundToInt(ratingY)}).")
    println("Expected outcome for X: ${et.roundToInt(eX * 100)}%")
    println("Expected outcome for Y: ${et.roundToInt(eY * 100)}%")

    val sX = 1.0  // actual game outcome
    val k = 40

    val ratingANew = et.newRating(ratingX, k, sX, eX)
    val ratingBNew = et.newRating(ratingY, k, sX, eX)

    println("\nPlayer A New Rating: ${et.roundToInt(ratingANew)}")
    println("Player B New Rating: ${et.roundToInt(ratingBNew)}")

    val eZ = 0.0
    val opponentRating = et.opponentRating(ratingX, eZ)
    println("\nPlayer Z (1500) wins against their opponent $eZ of the time.")
    println("  Expected opponent rating: ${et.roundToInt(opponentRating)}")
}


class EloTool() {

    /* Return the expected outcome (probability to win) for player A
       rA: rating of player A
       rB: rating of player B */
    fun expectedOutcome(rA: Double, rB: Double): Double{
        val exponent: Double = (rB - rA) / 400
        val base = 10.0
        return 1 / (1 + base.pow(exponent))
    }

    /* This is a shifted around version of the expected outcome formula
       Return the expected rating of the opponent that player A beats with a ratio of eA
       rA: rating for player A
       eA: expected outcome for player A */
    fun opponentRating(rA: Double, eA: Double): Double{
        val numer = ln((1/eA) - 1)
        val denom = ln(10.0)
        return 400 * (numer / denom) + rA
    }

    /* Returns the rating of player A after the game
       rA: rating of player A
       rB: rating of player B
       k: k-factor (controls sensitivity: k=40 for under 30 games, k=20 otherwise)
       sA: actual game outcome for player A (1, 0, 0.5)
       eA: expected game outcome for player A */
    fun newRating(rA: Double, k: Int, sA: Double, eA: Double) : Double {
        return rA + k * (sA - eA)
    }

    // Round decimal to the nearest integer
    fun roundToInt(value: Double): Int {
        return round(value).toInt()
    }
}