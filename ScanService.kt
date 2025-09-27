class ScanService : Service() {
    
    private val binder = ScanBinder()
    private var scanThread: Thread? = null
    
    inner class ScanBinder : Binder() {
        fun getService(): ScanService = this@ScanService
    }
    
    override fun onBind(intent: Intent): IBinder = binder
    
    fun startBackgroundScan(scanType: String, callback: (ScanResult) -> Unit) {
        scanThread = Thread {
            val result = when (scanType) {
                "quick" -> VirusScanner(this).performQuickScan()
                "deep" -> VirusScanner(this).performDeepScan()
                else -> throw IllegalArgumentException("Unknown scan type")
            }
            callback(result)
        }.apply { start() }
    }
}
