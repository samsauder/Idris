package model

import constants.Styles
import model.auxiliary.Type
import java.time.LocalDate

// model.Plan

abstract class Plan (val id: String, val type: Type, val skill: Skill, val subskills: List<Skill>?, var startDate: String?) {
    var active: Boolean = false
    //var today = LocalDate.now()

    fun start() {
        println("(start)")
        startDate = LocalDate.now() as String?
        active = true

    }

    fun printPlan(startLevel: Int) {
        val lvl0: String = " ".repeat(startLevel * 4)
        val lvl1: String = " ".repeat((startLevel + 1) * 4)
        val lvl2: String = " ".repeat((startLevel + 2) * 4)

        var planSymbol: String = ""

        when (type) {
            Type.day -> {planSymbol = "▲"}
            Type.week -> {planSymbol = "■"}
            // model.auxiliary.Type.month -> {planSymbol = ""}
            // model.auxiliary.Type.year -> {planSymbol = ""}
            else -> {}
        }

        val RESET: String = "\u001b[0m"
        val BLACK: String = "\u001b[30m"
        val ITALIC: String = "\u001B[3m"
        val RED: String = "\u001b[31m"
        val GREEN: String = "\u001b[32m"

        println("${lvl0}$GREEN[PLAN $planSymbol]$RESET")

        if (subskills != null) {
            // println("$lvl2")
        println("${lvl1}ID  $ITALIC$id$RESET")
        //println("${lvl1}TYPE  $type")
        //println("${lvl1}SKILL ${skill.id}")
        print("${lvl1}SUBSKILLS  ")
            for (s in subskills) {
                print("${Styles.BOLD}${s.id}$RESET ")
            }
            println()
        }
        println("${lvl1}START DATE  $startDate")
    }
}