package org.archguard.copilot.exec.agent

class ExecAgent {
    fun createPackage(packageName: String) {

    }

    fun createClass(className: String, body: ClassBody.() -> Unit) {

    }
}

class ClassBody {

}


fun exec(init: ExecAgent.() -> Unit): ExecAgent {
    val agent = ExecAgent()
    agent.init()
    return agent
}
