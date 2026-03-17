package com.idris.system.extra

// The specific strings used to represent each operation in the CLI


object OperationSymbols {
    const val LEN = 1  // allowed length of each operation string

    // Admin
    const val CREATE = "c"
    const val DELETE = "d"
    const val MODIFY = "m"

    // User
    const val LOG = "g"
    const val LIST = "l"
    const val VIEW = "v"
    const val STATE = "s"

    const val ADMIN = "a"
    const val HELP = "h"
    const val QUIT = "q"

    val all = listOf(CREATE, DELETE, MODIFY,
                     LOG, LIST, VIEW, STATE,
                     ADMIN, HELP)
}