package org.archguard.codedb.fitness

class FitnessDeclaration(val name: String) {
    /**
     * a detail will should show the detail of report
     */
    val detail: HashMap<String, List<Any>> = hashMapOf()
}

class Fitness(val value: Double) {

}

fun fitness(name: String, function: FitnessDeclaration.() -> Unit) {
    FitnessDeclaration(name).function()
}
