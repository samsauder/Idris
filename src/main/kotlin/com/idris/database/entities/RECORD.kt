package com.idris.database.entities

import com.idris.system.concepts.Record
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


object RECORDS : CONCEPTS("recordsT") {
    val objectiveName = varchar("oname", 60)  // objective name (may be challenge, exam, foundation)
    val won = bool("won")
    val date = varchar("date", 30)  // string representation of the date

    fun insert(r: Record) {
        // TODO add completeness check

        transaction {
            RECORD.new {
                this.name = r.name!!
                this.skillName = r.skillName!!
                this.description = r.description!!
                this.objectiveName = r.objectiveName!!
                this.won = r.won!!
                this.date = r.date!!
            }
        }
    }

    fun modify(name: String, r: Record) {
        transaction {
            RECORD.findSingleByAndUpdate(RECORDS.name eq name) {
                if (r.name != null) it.name = r.name!!
                if (r.skillName != null) it.skillName = r.skillName!!
                if (r.description != null) it.description = r.description!!
                if (r.objectiveName != null) it.objectiveName = r.objectiveName!!
                if (r.won != null) it.won = r.won!!
                if (r.date != null) it.date = r.date!!
            }
        }
    }
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