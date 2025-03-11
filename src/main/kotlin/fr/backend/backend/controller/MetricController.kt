package fr.backend.backend.controller

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/metrics")
class MetricsController(private val meterRegistry: MeterRegistry) {

    @GetMapping("/cpu")
    fun getCpuUsage(): Map<String, Any> {
        val cpuUsage = meterRegistry.find("system.cpu.usage").gauge()?.value() ?: 0.0
        return mapOf("cpuUsage" to cpuUsage)
    }

    @GetMapping("/memory")
    fun getMemoryUsage(): Map<String, Any> {
        val memoryUsed = meterRegistry.find("jvm.memory.used").gauge()?.value() ?: 0.0
        val memoryMax = meterRegistry.find("jvm.memory.max").gauge()?.value() ?: 1.0
        return mapOf(
            "memoryUsed" to memoryUsed,
            "memoryMax" to memoryMax,
            "memoryUsagePercent" to (memoryUsed / memoryMax) * 100
        )
    }

    @GetMapping("/threads")
    fun getThreadCount(): Map<String, Any> {
        val threadCount = meterRegistry.find("jvm.threads.live").gauge()?.value()?.toInt() ?: 0
        return mapOf("threadCount" to threadCount)
    }
}
