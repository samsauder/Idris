package com.idris.database.entities

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable


abstract class CONCEPTS(tableName: String) : IntIdTable(tableName) {
    val name = varchar("name", 50)
    val skillName = varchar("skillName", 50)
    val description = varchar("description", 200)

    // Add a CONCEPT to its proper table
    // abstract fun insert()
}