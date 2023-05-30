package org.archguard.copilot.exec.agent

class ExecAgent {
    fun createPackage(packageName: String) {
        TODO("Not yet implemented")
    }

}


fun exec(init: ExecAgent.() -> Unit): ExecAgent {
    val html = ExecAgent()
    html.init()
    return html
}
