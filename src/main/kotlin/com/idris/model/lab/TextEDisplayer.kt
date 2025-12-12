package com.idris.model.lab

class TextEDisplayer : EDisplayer() {

    // Display a text-based dashboard to the user
    override fun dashboardOf(e: Experiment) {
        println("\n\n========================================================================================")
        // title
        println("NAME        ${e.name}")

        // description
        println("\nDESCRIPTION  ${e.description}")

        // duration (days)
        println("\nDURATION     ${e.segment.size * e.segCount} days")

        println("\nSEGMENT")
        for (day in e.segment) {
            if (day != null) {
                println("    $day")
                /*
                for (objective in day) {
                    objective.printShort(0)
                } */
            } else {
                println("    N/A")
            }
        }

        println("========================================================================================\n")
    }
}