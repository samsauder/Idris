package com.idris.database.entities

import com.idris.system.concepts.Challenge
import com.idris.system.concepts.Record
import com.idris.system.extra.Util.datetimeNow
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal


// A ChallengesT inherits the name, skill, description, and minutes properties from ObjectiveTable
object CHALLENGES : OBJECTIVES("challengesT") {
    val progressionName = varchar("progressionName", 50)
    val cElo = decimal("cElo",6, 2)     // challenge elo
    val uElo = decimal("uElo", 6, 2)    // user elo
    val uOdds = decimal("uOdds", 3, 2)  // user win odds
    val attempts = integer("attempts")  // how many times has the user attempted
    val wins = integer("wins")  // how many times has the user won


    fun insert(c: Challenge) {
        transaction {
            CHALLENGE.new {
                this.name = c.name
                this.progressionName = c.progressionName
                this.skillName = c.skillName
                this.description = c.description
                this.minutes = c.minutes.toBigDecimal()
                this.cElo = BigDecimal("0.0")
                this.uElo = BigDecimal("0.0")
                this.uOdds = BigDecimal("1.0")
                this.attempts = 0
                this.wins = 0
            }
        }
    }

    fun update(c: Challenge, result: Double) {
        c.update(result == 1.0)

        transaction {
            CHALLENGE.findSingleByAndUpdate(CHALLENGES.name eq c.name) {
                it.cElo = BigDecimal.valueOf(c.challengeElo)
                it.uElo = BigDecimal.valueOf(c.userElo)
                it.uOdds = BigDecimal.valueOf(c.userOdds)
                it.attempts++
                it.wins += result.toInt()

                val datetime = datetimeNow()
                val r = Record("${it.name}___${datetime}", it.skillName, "", it.name, result == 1.0, datetime)
                RECORDS.insert(r)
            }
        }
    }
}

class CHALLENGE(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<CHALLENGE>(CHALLENGES) {
        // Return the ChallengeE with the specified name
        fun getOneNamed(name: String): CHALLENGE? {
            return find { CHALLENGES.name eq name}.singleOrNull()
        }
    }

    var name by CHALLENGES.name
    var skillName by CHALLENGES.skillName
    var description: String by CHALLENGES.description
    var minutes by CHALLENGES.minutes
    var progressionName by CHALLENGES.progressionName
    var cElo by CHALLENGES.cElo
    var uElo by CHALLENGES.uElo
    var uOdds by CHALLENGES.uOdds
    var attempts by CHALLENGES.attempts
    var wins by CHALLENGES.wins

    // Returns the Challenge version of the current ChallengeE
    override fun deEntify() : Challenge {
        return when (cElo) {
            BigDecimal("-0000.00") -> {  // cElo is uninitialized, use the determining constructor
                val c = Challenge(name, skillName, description, minutes.toDouble(), uOdds.toDouble())
                c.attempts = attempts
                c.wins = wins
                c.progressionName = progressionName
                c
            }

            else -> {  // cElo is initialized, use the supplying constructor
                val c = Challenge(
                    name,
                    skillName,
                    description,
                    minutes.toDouble(),
                    cElo.toDouble(),
                    uElo.toDouble(),
                    uOdds.toDouble()
                )
                c.attempts = attempts
                c.wins = wins
                c.progressionName = progressionName
                return c
            }
        }
    }

    override fun toString(): String {
        return "Challenge(id=$id, name=$name, skillName=$skillName)"  // Print a partial representation
    }
}