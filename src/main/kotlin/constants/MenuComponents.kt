package constants

object MenuComponents {
    // const val WIDTH = 60
    const val WIDTH = 70
    const val TITLE = "IDRISEUS"
    const val CHALLENGES_TITLE = "CHALLENGES"
    const val BUFFER_SIZE = 2
    const val SYM = "/"
    val BAR = SYM.repeat(WIDTH)
    val BAR2 = SYM.repeat((WIDTH - TITLE.length - BUFFER_SIZE) / 2)

    val TITLE_TOP = "\n|$BAR2${Styles.BOLD} $TITLE ${Styles.RESET}$BAR2|"
    val CHALLENGES_TOP = "\n|$BAR2 $CHALLENGES_TITLE $BAR2|"

    const val EDITOR = "[${Styles.YELLOW}1${Styles.RESET}] ${Styles.YELLOW}EDITOR${Styles.RESET}"
    const val STATS = "[${Styles.GREEN}2${Styles.RESET}] ${Styles.GREEN}STATS${Styles.RESET}"
    // const val OBJECTIVE_EDITOR = " [${Styles.YELLOW}2${Styles.RESET}] ${Styles.YELLOW}OBJECTIVE${Styles.RESET} editor"
    const val PLANS = "[${Styles.RED}3${Styles.RESET}] ${Styles.RED}PLANS${Styles.RESET}"
    // val VIEW_BENCHMARKS = " [${RED}${BOLD}4$RESET] ${RED}view$RESET BENCHMARKS"
    const val OBJECTIVES = "[${Styles.BLUE}4${Styles.RESET}] ${Styles.BLUE}OBJECTIVES${Styles.RESET}"
    const val EXIT = "[x] exit"
    val BOTTOM = "|$BAR|"
    const val PROMPT_SYM = ": "
}