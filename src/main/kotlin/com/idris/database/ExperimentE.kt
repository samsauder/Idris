package com.idris.database

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity

object ExperimentsT : IntIdTable("experimentsT") {

}

class ExperimentE(id: EntityID<Int>) : IntEntity(id) {

}