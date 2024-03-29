package org.archguard.runner.runner

import org.archguard.runner.context.HostContext

open class RunnerService {
    protected val hostContext: HostContext = HostContext(hostType = "Runner")
}
