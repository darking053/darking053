class ThunderApplication : Application() {
    
    companion object {
        lateinit var instance: ThunderApplication
            private set
    }
    
    private lateinit var threatDatabase: ThreatDatabaseHelper
    private lateinit var realTimeProtection: RealTimeProtection
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        initializeAppComponents()
        startBackgroundServices()
        schedulePeriodicUpdates()
    }
    
    private fun initializeAppComponents() {
        // Veritabanını başlat
        threatDatabase = ThreatDatabaseHelper(this)
        threatDatabase.initializeDatabase()
        
        // Gerçek zamanlı korumayı başlat
        realTimeProtection = RealTimeProtection(this)
        
        // Güncellemeleri kontrol et
        UpdateManager.initialize(this)
    }
    
    private fun startBackgroundServices() {
        // Gerçek zamanlı koruma servisini başlat
        if (PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("real_time_protection", true)) {
            realTimeProtection.enableRealTimeProtection()
        }
        
        // Güncelleme servisini başlat
        val updateWorkRequest = PeriodicWorkRequestBuilder<UpdateWorker>(
            repeatInterval = 12, TimeUnit.HOURS
        ).build()
        
        WorkManager.getInstance(this).enqueue(updateWorkRequest)
    }
    
    fun getThreatDatabase(): ThreatDatabaseHelper = threatDatabase
    fun getRealTimeProtection(): RealTimeProtection = realTimeProtection
}
