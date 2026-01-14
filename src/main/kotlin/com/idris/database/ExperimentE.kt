package com.idris.database

import com.idris.model.lab.Experiment
import com.idris.model.newclasses.Concept
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


object ExperimentsT : IntIdTable("experimentsT") {
    val name = varchar("name", 50)
    val skillName = varchar("skillName", 50)
    val description = varchar("description", 400)

    // a segment consists of 7 Day names (from the DaysT table)
    val d1 = varchar("day1Name", 50) // day 1 reference
    val d2 = varchar("day2Name", 50) // day 2 reference
    val d3 = varchar("day3Name", 50) // day 3 reference
    val d4 = varchar("day4Name", 50) // day 4 reference
    val d5 = varchar("day5Name", 50) // day 5 reference
    val d6 = varchar("day6Name", 50) // day 6 reference
    val d7 = varchar("day7Name", 50) // day 7 reference

    var segCount = integer("segCount")  // total # of repeating segments

    // val startDate
}

class ExperimentE(id: EntityID<Int>) : ConceptE(id) {
    companion object : IntEntityClass<ExperimentE>(ExperimentsT) {
        fun getOneNamed(name: String): ExperimentE? {
            return find { ExperimentsT.name eq name}.singleOrNull()
        }
    }

    var name by ExperimentsT.name
    var skillName by ExperimentsT.skillName
    var description by ExperimentsT.description

    var d1 by ExperimentsT.d1
    var d2 by ExperimentsT.d2
    var d3 by ExperimentsT.d3
    var d4 by ExperimentsT.d4
    var d5 by ExperimentsT.d5
    var d6 by ExperimentsT.d6
    var d7 by ExperimentsT.d7

    var segCount by ExperimentsT.segCount

    override fun deEntify(): Concept {  // provisionally done
        // TODO("Not yet implemented")
        val dN = listOf(d1, d2, d3, d4, d5, d6, d7)

        val x = Experiment(
            name,
            skillName,
            description,
            dN,
            segCount
        )
        return x
    }
    // var startDate
}