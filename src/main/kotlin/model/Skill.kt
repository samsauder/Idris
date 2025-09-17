package model

import model.challenge.Challenge

// Subdomain

class Skill (val id: String, val subskills: MutableList<Skill>?) {
    var challenges: MutableList<Challenge> = mutableListOf()
        // get() = this.benchmarks
        set(value) {
            field = value
        }
}