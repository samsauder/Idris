package com.idris.database.entities

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


object RECORDS : CONCEPTS("recordsT") {
    val objectiveName = varchar("oname", 50)  // objective name (may be challenge, exam, foundation)
    val won = bool("won")
    val date = varchar("date", 10)  // string representation of the date
}

class RECORD(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RECORD>(RECORDS)

    var name by RECORDS.name
    var skillName by RECORDS.skillName
    var description by RECORDS.description

    var objectiveName by RECORDS.objectiveName
    var won by RECORDS.won
    var date by RECORDS.date
}