package com.idris.database

import com.idris.model.Foundation
import com.idris.model.Skill
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


// The table of Foundations
object FoundationsT : IntIdTable("foundationsT") {
    val name = varchar("name", 50)
    val skill = varchar("skill", 50)
    val description = varchar("description", 200)
    val minutes = decimal("minutes",5, 2)
}

class FoundationE(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FoundationE>(FoundationsT) {

    }

    var name by FoundationsT.name
    var skill: String by FoundationsT.skill
    var description by FoundationsT.description
    var minutes by FoundationsT.minutes

    // Returns the Foundation version of the current FoundationE
    fun toFoundation() : Foundation{
        return Foundation(
            name,
            Skill(skill, null),
            description,
            minutes.toDouble()
        )
    }
}