package com.idris.database

import com.idris.model.Skill
import com.idris.model.challenge.Challenge
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal


object ChallengesT : IntIdTable("challengesT") {
    val name = varchar("name", 50)
    val skill = varchar("skill", 50)
    val description = varchar("description", 200)
    val minutes = decimal("minutes",5, 2)
    val cElo = decimal("cElo",6, 2)     // challenge elo
    val uElo = decimal("uElo", 6, 2)    // user elo
    val uOdds = decimal("uOdds", 3, 2)  // user win odds
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
    var description by ChallengesT.description
    var minutes by ChallengesT.minutes
    var cElo by ChallengesT.cElo
    var uElo by ChallengesT.uElo
    var uOdds by ChallengesT.uOdds

    // Returns the Challenge version of the current ChallengeE
    fun toChallenge() : Challenge {
        return when (cElo) {
            BigDecimal("-0000.00") -> {  // cElo is uninitialized, use the determining constructor
                Challenge(name, Skill(skill, null), description, minutes.toDouble(), uOdds.toDouble())
            }

            else -> {  // cElo is initialized, use the supplying constructor
                Challenge(
                    name,
                    Skill(skill, null),
                    description,
                    minutes.toDouble(),
                    cElo.toDouble(),
                    uElo.toDouble(),
                    uOdds.toDouble()
                )
            }
        }
    }

    override fun toString(): String {
        return "Challenge(id=$id, name=$name, skill=$skill)"  // Print a partial representation
    }
}