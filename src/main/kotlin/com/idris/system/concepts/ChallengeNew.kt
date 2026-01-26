package com.idris.system.concepts

import jdk.jfr.Description

class ChallengeNew : Objective {
    override val icon = "●"
    var elo: Double = 0.0

    constructor(name: String,
                skillName: String,
                description: String,
                minutes: Double) : super(name, skillName, description, minutes)

    override fun log(value: Double) {
        TODO("Not yet implemented")
    }

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }
}