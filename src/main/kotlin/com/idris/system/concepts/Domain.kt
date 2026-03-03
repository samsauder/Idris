package com.idris.system.concepts

import com.idris.system.extra.ConceptType


class Domain : Concept {
    override val icon: String = "O"
    override val ct = ConceptType.DAY  // TODO change DAY to DOMAIN after adding DOMAIN to ConceptType
    val superdomain: String?

    constructor(name: String,
                skillName: String,
                description: String,
                superdomain: String?) : super(name, skillName, description) {
        this.superdomain = superdomain
    }


    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }
}