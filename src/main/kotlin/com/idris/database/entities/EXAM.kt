package com.idris.database.entities

import com.idris.system.concepts.Exam
import com.idris.system.concepts.Record
import com.idris.system.extra.Util.datetimeNow
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.math.BigDecimal


object EXAMS : OBJECTIVES("examsT") {
    val passed = bool("passed")


    fun insert(e: Exam) {
        transaction {
            EXAM.new {
                this.name = e.name
                this.skillName = e.skillName
                this.description = e.description
                this.minutes = e.minutes.toBigDecimal()
                this.passed = e.passed
            }
        }
    }

    fun update(e: Exam, result: Double) {
        val passed = result == 1.0
        e.update(passed)

        EXAM.findSingleByAndUpdate(EXAMS.name eq e.name) {
            it.passed = passed
            val datetime = datetimeNow()
            val r = Record("${it.name}___${datetime}", it.skillName, "", it.name, result == 1.0, datetime)
            RECORDS.insert(r)
        }
    }
}

class EXAM(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<EXAM>(EXAMS) {
        fun getOneNamed(name: String): EXAM? {
            return find { EXAMS.name eq name}.singleOrNull()
        }
    }

    var name by EXAMS.name
    var skillName: String by EXAMS.skillName
    var description by EXAMS.description
    var minutes: BigDecimal by EXAMS.minutes
    var passed by EXAMS.passed

    // Turn the ExamE into an Exam
    override fun deEntify() : Exam {
        return Exam(
            name,
            skillName,
            description,
            minutes.toDouble(),
            passed)
    }
}