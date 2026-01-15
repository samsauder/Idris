package com.idris.database

import com.idris.model.objective.Foundation
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass

// IntIdTable("foundationsT")


object FOUNDATIONS : OBJECTIVES("foundationsT") {

}


class FOUNDATION(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<FOUNDATION>(FOUNDATIONS) {
        fun getOneNamed(name: String): FOUNDATION? {
            return find { FOUNDATIONS.name eq name}.singleOrNull()
        }
    }

    var name by FOUNDATIONS.name
    var skillName: String by FOUNDATIONS.skillName
    // var skill: String by FoundationsT.skill
    var description by FOUNDATIONS.description
    var minutes by FOUNDATIONS.minutes

    // Turn the FoundationE into a Foundation
    override fun deEntify() : Foundation {
        return Foundation(
            name,
            skillName,
            // Skill(skill, null),
            description,
            minutes.toDouble())
    }
}