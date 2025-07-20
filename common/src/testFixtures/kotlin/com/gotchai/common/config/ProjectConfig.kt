package com.gotchai.common.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode

class ProjectConfig : AbstractProjectConfig() {
    override val parallelism: Int = 3
    override val isolationMode: IsolationMode = IsolationMode.InstancePerLeaf
}
