package model.lab

abstract class EDisplayer {
    // Display the dashboard of the given Experiment to the user
    abstract fun dashboardOf(e: Experiment)
}