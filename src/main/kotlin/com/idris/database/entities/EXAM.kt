package com.idris.database.entities

import com.idris.model.concepts.Exam
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass
import java.math.BigDecimal


object EXAMS : OBJECTIVES("examsT") {
    val passed = bool("passed")
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