object UpdateManager {
    
    private const val UPDATE_URL = "https://api.thunderantivirus.com/signatures/latest"
    
    suspend fun checkForUpdates(context: Context): UpdateResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = HttpClient().get<String>(UPDATE_URL)
                val updateInfo = parseUpdateResponse(response)
                
                if (isUpdateAvailable(updateInfo.version)) {
                    downloadSignatureUpdates(updateInfo.signatures)
                    UpdateResult.SUCCESS
                } else {
                    UpdateResult.NO_UPDATE
                }
            } catch (e: Exception) {
                UpdateResult.FAILED
            }
        }
    }
    
    private fun downloadSignatureUpdates(signatures: List<ThreatSignature>) {
        signatures.forEach { signature ->
            ThreatDatabaseHelper(context).addThreatSignature(
                signature.signature,
                signature.name,
                signature.type,
                signature.severity
            )
        }
    }
}
