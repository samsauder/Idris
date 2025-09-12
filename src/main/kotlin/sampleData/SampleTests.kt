package sampleData

import constants.Descriptions
import model.Test

// Sam
val pullup2RMplus50lb = Test("plus50pullup2RM", pull, Descriptions.PULLUP_2RM_PLUS_50LB , 1)
val bench1RM80lb = Test("bench1RM80lb", antagonist, Descriptions.BENCH_1RM_80LB , 1)

val boulderingPlan1Tests = mutableListOf(pullup2RMplus50lb)
val boulderingPlan2Tests = mutableListOf(bench1RM80lb)


// Will
val yellowBeltTest = Test("yellowBeltTest", karate, Descriptions.YELLOW_BELT_TEST, 30)

val karateTests = mutableListOf(yellowBeltTest)