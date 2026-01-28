package com.idris.system.concepts


class Record : Concept {
    override val icon = "R"
    var objectiveName: String       // name of completed f/c/e
    var won: Boolean                // success/failure
    var date: String                // date completed

    constructor(name: String,
                skillName: String,
                description: String,
                objectiveName: String,
                won: Boolean,
                date: String) : super(name, skillName, description) {
        this.objectiveName = objectiveName
        this.won = won
        this.date = date
    }

    override fun print() {
        TODO("Not yet implemented")
    }

    override fun printL() {
        TODO("Not yet implemented")
    }

}