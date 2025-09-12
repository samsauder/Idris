package constants

import constants.Styles

object MenuComponents {
    const val WIDTH = 60
    const val TITLE = "IDRISEUS"
    const val BUFFER_SIZE = 2
    const val SYM = "/"
    val BAR = SYM.repeat(WIDTH)
    val BAR2 = SYM.repeat((WIDTH - TITLE.length - BUFFER_SIZE) / 2)

    val TOP = "\n|$BAR2${Styles.BOLD} $TITLE ${Styles.RESET}$BAR2|"
    const val PLAN_EDITOR = " [${Styles.GREEN}1${Styles.RESET}] ${Styles.GREEN}PLAN${Styles.RESET} editor"
    const val OBJECTIVE_EDITOR = " [${Styles.YELLOW}2${Styles.RESET}] ${Styles.YELLOW}OBJECTIVE${Styles.RESET} editor"
    const val VIEW_PLANS = " [${Styles.RED}3${Styles.RESET}] ${Styles.RED}view${Styles.RESET} PLANS ${Styles.RED}${Styles.RESET}"
    // val VIEW_BENCHMARKS = " [${RED}${BOLD}4$RESET] ${RED}view$RESET BENCHMARKS"
    const val LOG_OBJECTIVE = " [${Styles.BLUE}4${Styles.RESET}] ${Styles.BLUE}log${Styles.RESET} OBJECTIVE"
    const val EXIT = " [x] exit"
    val BOTTOM = "|$BAR|"
    const val PROMPT_SYM = ": "
}