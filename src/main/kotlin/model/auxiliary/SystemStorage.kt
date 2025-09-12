package model.auxiliary

import model.Challenge
import model.Plan

// Stores all benchmarks and plans of the current instance of the system (add tests and tasks later)
class SystemStorage(val challenges: List<Challenge>, val plans: List<Plan>?) { }