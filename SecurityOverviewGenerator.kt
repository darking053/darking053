object SecurityOverviewGenerator {
    
    suspend fun generateOverview(context: Context): SecurityOverview {
        return withContext(Dispatchers.IO) {
            val scanner = VirusScanner(context)
            val networkSecurity = NetworkSecurity(context)
            val dbHelper = ThreatDatabaseHelper(context)
            
            SecurityOverview(
                installedApps = getInstalledAppsCount(context),
                scannedToday = getScannedTodayCount(dbHelper),
                threatsBlocked = getThreatsBlockedCount(dbHelper),
                networkStatus = networkSecurity.checkNetworkSecurity(),
                lastUpdate = UpdateManager.getLastUpdateDate(context),
                systemSecurity = checkSystemSecurity(context)
            )
        }
    }
}
