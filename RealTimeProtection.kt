class RealTimeProtection(private val context: Context) {
    
    private val fileMonitor = FileSystemMonitor()
    private val networkMonitor = NetworkTrafficMonitor()
    private val appInstallMonitor = AppInstallMonitor()
    
    fun enableRealTimeProtection() {
        startFileMonitoring()
        startNetworkMonitoring()
        startAppInstallMonitoring()
        
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean("real_time_protection", true)
            .apply()
    }
    
    private fun startFileMonitoring() {
        fileMonitor.startWatching(
            directories = arrayOf(
                Environment.getExternalStorageDirectory().path,
                "/sdcard/Download"
            ),
            callback = { filePath, event ->
                when (event) {
                    FileEvent.CREATE -> onFileCreated(filePath)
                    FileEvent.MODIFY -> onFileModified(filePath)
                    FileEvent.DELETE -> onFileDeleted(filePath)
                }
            }
        )
    }
    
    private fun onFileCreated(filePath: String) {
        if (isExecutableFile(filePath)) {
            val threatCheck = VirusScanner(context).analyzeFile(filePath)
            threatCheck?.let { threat ->
                showThreatAlert(threat)
                // Tehdidi karantinaya al
                QuarantineManager.quarantineFile(context, filePath)
            }
        }
    }
}
