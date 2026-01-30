package com.idris.database.entities

import com.idris.system.concepts.Record
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass


object RECORDS : CONCEPTS("recordsT") {
    val objectiveName = varchar("oname", 60)  // objective name (may be challenge, exam, foundation)
    val won = bool("won")
    val date = varchar("date", 30)  // string representation of the date
}

class RECORD(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<RECORD>(RECORDS) {
        fun getOneNamed(name: String): RECORD? {
            return find { RECORDS.name eq name}.singleOrNull()
        }
    }

    var name by RECORDS.name
    var skillName by RECORDS.skillName
    var description by RECORDS.description

    var objectiveName by RECORDS.objectiveName
    var won by RECORDS.won
    var date by RECORDS.date

    override fun deEntify(): Record {
        return Record(name, skillName, description, objectiveName, won, date)
    }
}