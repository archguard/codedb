package org.archguard.codedb.build.task.api

interface Task : Comparable<Task> {
    var name: String
    var description: String
    var group: String
    var dependsOn: Set<String>


    fun getInputs(): TaskInputs

    fun getOutputs(): TaskOutputs

    fun setDependsOn(dependsOnTasks: Iterable<*>) {}

    fun dependsOn(vararg tables: Any): Task {
        return this
    }

    // TODO: add some DAG, like before and after
}

interface TaskInputs {

}

interface TaskOutputs {

}
