package model.lab
import com.sun.jdi.IntegerType
import model.Exam
import model.Foundation
import model.Objective
import model.Skill
import model.challenge.Challenge


// Sam Sauder
// Dec 5 2025
// An Experiment is plan of action to achieve a PRIMARY goal and a set of its AUXILIARY goals over a certain duration.
// + title
// + description       |  precise specification of the experiment
// + start date        |  what day was the experiment begun
// + practiceSegmentCt |  # of practice segments in the entire experiment
// + measureSegmentCt  |  1
// + segmentDuration   |  length of practiceSegment (days)
// + practiceSegment   |  an array of Skill lists for each day in a segment (may have gaps)
// + measureSegment    |  an array of Exam lists for each day in the segment    (may have gaps)
// + foundations       |  an array of all Foundations
// + challenges        |  an array of all Challenges
// + exams             |  an array of all Exams
// + adherence         |  current # of days completed / projected # of days completed

class Experiment(var title: String,
                 var description: String,
                 var segment: Array<List<Skill>>,
                 var segmentCt: Int,
                 var foundations: Array<Foundation>,
                 var challenges: Array<Challenge>,
                 var exams: Array<Exam>) {

    // private var adherence: Double
    // start date
}