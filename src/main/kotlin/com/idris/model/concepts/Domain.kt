package com.idris.model.concepts


class Domain : Concept {
    override val icon: String = "O"
    val superdomain: Domain?

    constructor(name: String,
                skillName: String,
                description: String,
                superdomain: Domain?) : super(name, skillName, description) {
        this.superdomain = superdomain
    }


    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }
}


fun main() {

}