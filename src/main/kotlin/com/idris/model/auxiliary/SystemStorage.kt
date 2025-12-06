package com.idris.model.auxiliary

import com.idris.model.Plan
import com.idris.model.challenge.Challenge

// Stores all benchmarks and plans of the current instance of the system (add tests and tasks later)
class SystemStorage(val challenges: List<Challenge>, val plans: List<Plan>?) { }