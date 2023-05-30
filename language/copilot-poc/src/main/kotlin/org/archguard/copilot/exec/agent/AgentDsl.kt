package org.archguard.copilot.exec.agent

enum class ArchStyle(val value: String) {
    MVC("mvc"),
    MVP("mvp"),
    MVVM("mvvm"),
    VIPER("viper"),
    CLEAN("clean"),
    DDD("ddd"),
    ONION("onion"),
    HEXAGONAL("hexagonal");

    companion object {
        fun contains(string: String): Boolean {
            val lowercase = string.lowercase()
            return values().any { it.value == lowercase }
        }
    }
}

class ExecAgent {
    private var workdir: String = ""
    private var archStyle: ArchStyle = ArchStyle.MVC

    fun workspace(path: String) {
        this.workdir = path
    }

    fun archStyle(string: String) {
        // parse string to ArchStyle
        if (ArchStyle.contains(string)) {
            this.archStyle = ArchStyle.valueOf(string.uppercase())
        }
    }

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
