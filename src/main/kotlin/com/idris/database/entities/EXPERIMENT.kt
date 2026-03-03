package com.idris.database.entities

import com.idris.system.concepts.Experiment
import com.idris.system.concepts.Concept
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


object EXPERIMENTS : IntIdTable("experimentsT") {
    var name = varchar("name", 50)
    var skillName = varchar("skillName", 50)
    var description = varchar("description", 400)

    // a segment consists of 7 Day names (from the DaysT table)
    val d1 = varchar("day1Name", 50) // day 1 reference
    val d2 = varchar("day2Name", 50) // day 2 reference
    val d3 = varchar("day3Name", 50) // day 3 reference
    val d4 = varchar("day4Name", 50) // day 4 reference
    val d5 = varchar("day5Name", 50) // day 5 reference
    val d6 = varchar("day6Name", 50) // day 6 reference
    val d7 = varchar("day7Name", 50) // day 7 reference

    var segCount = integer("segCount")  // total # of repeating segments


    fun insert(x: Experiment) {
        // TODO add completeness check

        transaction {
            EXPERIMENT.new {
                this.name = x.name!!
                this.skillName = x.skillName!!
                this.description = x.description!!
                this.d1 = x.segment[0]!!
                this.d2 = x.segment[1]!!
                this.d3 = x.segment[2]!!
                this.d4 = x.segment[3]!!
                this.d5 = x.segment[4]!!
                this.d6 = x.segment[5]!!
                this.d7 = x.segment[6]!!
                this.segCount = x.segCount
            }
        }

        // message
    }

    fun modify(name: String, x: Experiment) {
        transaction {
            EXPERIMENT.findSingleByAndUpdate(EXPERIMENTS.name eq name) {
                if (x.name != null) it.name = x.name!!
                if (x.skillName != null) it.skillName = x.skillName!!
                if (x.description != null) it.description = x.description!!

                if (x.segment[0] != null) it.d1 = x.segment[0]!!
                if (x.segment[1] != null) it.d2 = x.segment[1]!!
                if (x.segment[2] != null) it.d3 = x.segment[2]!!
                if (x.segment[3] != null) it.d4 = x.segment[3]!!
                if (x.segment[4] != null) it.d5 = x.segment[4]!!
                if (x.segment[5] != null) it.d6 = x.segment[5]!!
                if (x.segment[6] != null) it.d7 = x.segment[6]!!

                if (x.segCount != null) it.segCount = x.segCount
            }
        }

        // message
    }
}


class EXPERIMENT(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<EXPERIMENT>(EXPERIMENTS) {
        fun getOneNamed(name: String): EXPERIMENT? {
            return find { EXPERIMENTS.name eq name}.singleOrNull()
        }
    }

    var name by EXPERIMENTS.name
    var skillName by EXPERIMENTS.skillName
    var description by EXPERIMENTS.description

    var d1 by EXPERIMENTS.d1
    var d2 by EXPERIMENTS.d2
    var d3 by EXPERIMENTS.d3
    var d4 by EXPERIMENTS.d4
    var d5 by EXPERIMENTS.d5
    var d6 by EXPERIMENTS.d6
    var d7 by EXPERIMENTS.d7

    var segCount by EXPERIMENTS.segCount

    override fun deEntify(): Concept {  // provisionally done
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
}