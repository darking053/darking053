class ThreatDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        const val DATABASE_NAME = "ThunderAntivirus.db"
        const val DATABASE_VERSION = 1
        const val TABLE_THREATS = "threats"
        const val TABLE_SCAN_HISTORY = "scan_history"
    }
    
    override fun onCreate(db: SQLiteDatabase) {
        createThreatsTable(db)
        createScanHistoryTable(db)
        insertDefaultThreatSignatures(db)
    }
    
    private fun createThreatsTable(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_THREATS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                signature TEXT UNIQUE,
                threat_name TEXT,
                threat_type TEXT,
                severity INTEGER,
                last_updated INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }
    
    fun addThreatSignature(signature: String, name: String, type: String, severity: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("signature", signature)
            put("threat_name", name)
            put("threat_type", type)
            put("severity", severity)
            put("last_updated", System.currentTimeMillis())
        }
        db.insert(TABLE_THREATS, null, values)
    }
    
    fun getThreatSignature(signature: String): ThreatSignature? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_THREATS WHERE signature = ?"
        val cursor = db.rawQuery(query, arrayOf(signature))
        
        return if (cursor.moveToFirst()) {
            ThreatSignature(
                signature = cursor.getString(cursor.getColumnIndex("signature")),
                name = cursor.getString(cursor.getColumnIndex("threat_name")),
                type = cursor.getString(cursor.getColumnIndex("threat_type")),
                severity = cursor.getInt(cursor.getColumnIndex("severity"))
            )
        } else {
            null
        }.also { cursor.close() }
    }
}
