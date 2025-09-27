data class ScanResult(
    val scanType: String,
    val threatsFound: Int,
    val scannedItems: Int,
    val threats: List<ThreatInfo>,
    val timestamp: Long,
    val duration: Long = 0
)

data class ThreatInfo(
    val name: String,
    val type: ThreatType,
    val path: String,
    val description: String,
    val severity: Int = 1
)

enum class ThreatType {
    MALWARE, RISKWARE, ADWARE, RANSOMWARE, SUSPICIOUS
}

data class NetworkScanResult(
    val totalConnections: Int,
    val suspiciousConnections: List<SuspiciousConnection>,
    val vpnEnabled: Boolean,
    val proxyEnabled: Boolean
)
