package com.idris.model.newclasses

// The Concept is the superclass of all Idris classes and stores common attributes:
// + name        |  a unique identifier
// + description |  a brief (< 200 char) synopsis

// EXAMPLE for a Challenge (a Concept subclass)
// name        :  puzzle2300
// description :  Complete a 2300-2399 rated Lichess puzzle within 1 minute.


abstract class Concept {
    abstract val name: String
    abstract val description: String
}