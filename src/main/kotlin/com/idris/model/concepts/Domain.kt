package com.idris.model.concepts

// An area of expertise
// Keter domain: the domain that has all the highest level domains as components

class Domain : Concept {
    val superdomain: Domain?

    constructor(name: String, skillName: String, description: String, superdomain: Domain?)
            : super(name, skillName, description) {
        this.superdomain = superdomain
    }

    override val icon: String = "O"

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }
}


fun main() {

}