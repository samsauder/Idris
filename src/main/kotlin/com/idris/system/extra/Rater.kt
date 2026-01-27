package com.idris.system.extra

import com.idris.system.concepts.ChallengeNew
import com.idris.system.extra.Styler.style
import java.time.LocalDate

// The Rater is responsible for updating challenge rating


abstract class Rater {
    abstract fun update(challenge: ChallengeNew, won: Boolean)
}