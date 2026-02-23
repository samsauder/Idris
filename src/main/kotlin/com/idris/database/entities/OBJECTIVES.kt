package com.idris.database.entities

// Defines common attributes for all Idris Objective tables


abstract class OBJECTIVES(tableName: String) : CONCEPTS(tableName) {
    val minutes = decimal("minutes",5, 2)

    /*
    abstract fun insert(name: String,
                        skill: String,
                        description: String?,
                        minutes: Double)
     */
}