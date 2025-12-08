// Challenge Attempt Entity

package com.idris.database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


enum class Months {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC}

object ChAttemptsT : IntIdTable("chAttemptsT") {
    val challenge = reference("challenge", ChallengesT)
    val won = bool("won")
    // val month = enumeration("month", Months::class)
    val date = varchar("date", 10)  // string representation of the date
    // val day = integer("day")
}

class ChAttemptE(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChAttemptE>(ChAttemptsT)

    var challenge by ChallengeE referencedOn ChAttemptsT.challenge
    var won by ChAttemptsT.won
    // var month by ChAttemptsT.month
    // var day by ChAttemptsT.day
    var date by ChAttemptsT.date
}