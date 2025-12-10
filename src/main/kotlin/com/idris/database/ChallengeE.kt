package com.idris.database

import com.idris.model.Skill
import com.idris.model.objective.Challenge
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal

// A ChallengesT inherits the name, skill, description, and minutes properties from ObjectiveTable
object ChallengesT : ObjectiveTable("challengesT") {
    val cElo = decimal("cElo",6, 2)     // challenge elo
    val uElo = decimal("uElo", 6, 2)    // user elo
    val uOdds = decimal("uOdds", 3, 2)  // user win odds
    val attempts = integer("attempts")  // how many times has the user attempted
    val wins = integer("wins")  // how many times has the user won
}


class ChallengeE(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChallengeE>(ChallengesT) {
        // Return the ChallengeE with the specified name
        fun getChallengeByName(name: String): ChallengeE? {
            return find { ChallengesT.name eq name}.singleOrNull()
        }
    }

    var name by ChallengesT.name
    var skill by ChallengesT.skill
    var description: String by ChallengesT.description
    var minutes by ChallengesT.minutes
    var cElo by ChallengesT.cElo
    var uElo by ChallengesT.uElo
    var uOdds by ChallengesT.uOdds
    var attempts by ChallengesT.attempts
    var wins by ChallengesT.wins

    // Returns the Challenge version of the current ChallengeE
    fun deEntify() : Challenge {
        return when (cElo) {
            BigDecimal("-0000.00") -> {  // cElo is uninitialized, use the determining constructor
                val c = Challenge(name, Skill(skill, null), description, minutes.toDouble(), uOdds.toDouble())
                c.attempts = attempts
                c.wins = wins
                c
            }

            else -> {  // cElo is initialized, use the suppling constructor
                val c = Challenge(
                    name,
                    Skill(skill, null),
                    description,
                    minutes.toDouble(),
                    cElo.toDouble(),
                    uElo.toDouble(),
                    uOdds.toDouble()
                )
                c.attempts = attempts
                c.wins = wins
                return c
            }
        }
    }

    override fun toString(): String {
        return "Challenge(id=$id, name=$name, skill=$skill)"  // Print a partial representation
    }
}