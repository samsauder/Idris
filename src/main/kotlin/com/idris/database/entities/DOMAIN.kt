package com.idris.database.entities

import com.idris.system.concepts.Concept
import com.idris.system.concepts.Domain
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntityClass


object DOMAINS : CONCEPTS("domainsT") {
    val superdomain = varchar("superdomain", 50)
}

class DOMAIN(id: EntityID<Int>) : CONCEPT(id) {
    companion object : IntEntityClass<DOMAIN>(DOMAINS) {
        fun getOneNamed(name: String): DOMAIN? {
            return find { DOMAINS.name eq name}.singleOrNull()
        }
    }

    var name by DOMAINS.name
    var skillName by DOMAINS.skillName
    var description by DOMAINS.description
    var superdomain by DOMAINS.superdomain

    override fun deEntify(): Concept {
        return Domain(name, skillName, description, superdomain)
    }
}