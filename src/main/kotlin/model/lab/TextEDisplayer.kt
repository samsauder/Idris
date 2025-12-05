package model.lab

class TextEDisplayer : EDisplayer() {

    // Display a text-based dashboard to the user
    override fun dashboardOf(e: Experiment) {
        println("\n\n========================================================================================")
        // title
        println("TITLE        ${e.title}")

        // description
        println("\nDESCRIPTION  ${e.description}")

        // duration (days)
        println("\nDURATION     ${e.segment.size * e.segmentCt} days")

        println("\nFOUNDATIONS")
        for (f in e.foundations) {
            f.printShort(0);
        }

        println("\nCHALLENGES")
        for (c in e.challenges) {
            c.printShort(0);
        }

        println("\nEXAMS")
        for (e in e.exams) {
            e.printShort(0);
        }
        println("========================================================================================\n")
    }
}