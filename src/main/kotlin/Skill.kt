// Subdomain

class Skill (val id: String, val subskills: MutableList<Skill>?) {
    var benchmarks: MutableList<Benchmark> = mutableListOf()
        // get() = this.benchmarks
        set(value) {
            field = value
        }
}