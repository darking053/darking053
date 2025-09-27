object Constants {
    const val DATABASE_NAME = "thunder_antivirus.db"
    const val PREFERENCES_NAME = "thunder_preferences"
    const val UPDATE_URL = "https://api.thunderantivirus.com/v1/"
    
    // Tarama türleri
    const val SCAN_TYPE_QUICK = "quick"
    const val SCAN_TYPE_DEEP = "deep"
    const val SCAN_TYPE_CUSTOM = "custom"
    
    // Tehdit seviyeleri
    const val THREAT_LEVEL_LOW = 1
    const val THREAT_LEVEL_MEDIUM = 2
    const val THREAT_LEVEL_HIGH = 3
    const val THREAT_LEVEL_CRITICAL = 4
    
    // SharedPreferences keys
    const val PREF_REAL_TIME_PROTECTION = "real_time_protection"
    const val PREF_LAST_SCAN_DATE = "last_scan_date"
    const val PREF_AUTO_UPDATE = "auto_update"
}
