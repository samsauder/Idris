package com.idris.database.entities

import com.idris.system.concepts.Concept
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


abstract class CONCEPTS(tableName: String) : IntIdTable(tableName) {
    var name = varchar("name", 50)
    var skillName = varchar("skillName", 50)
    val description = varchar("description", 200)

    // Add the given CONCEPT to its proper table
    // abstract fun insert()
}