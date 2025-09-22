package sampleData

import constants.Descriptions
import model.Exam

// Sam
val pullup2RMplus50lb = Exam("plus50pullup2RM", pull, Descriptions.PULLUP_2RM_PLUS_50LB , 1.0)
/// val bench1RM80lb = Exam("bench1RM80lb", antagonist, Descriptions.BENCH_1RM_80LB , 1.0)

val boulderingLiExams = mutableListOf(pullup2RMplus50lb)
// val boulderingVoExams = mutableListOf(bench1RM80lb)


// Will
val yellowBeltExam = Exam("yellowBeltTest", karate, Descriptions.YELLOW_BELT_TEST, 30.0)

val karateTests = mutableListOf(yellowBeltExam)