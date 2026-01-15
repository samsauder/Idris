package com.idris.database.entities

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


object RECORDS : IntIdTable("chAttemptsT") {
    val challenge = reference("challenge", CHALLENGES)
    val won = bool("won")
    val date = varchar("date", 10)  // string representation of the date
}

class RECORD(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RECORD>(RECORDS)

    var challenge by CHALLENGE referencedOn RECORDS.challenge
    var won by RECORDS.won
    var date by RECORDS.date
}