package com.idris.system.concepts


class Domain : Concept {
    override val icon: String = "O"
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