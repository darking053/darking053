class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val scanner = VirusScanner(this)
    private val dbHelper = ThreatDatabaseHelper(this)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeApp()
        setupClickListeners()
    }
    
    private fun initializeApp() {
        // Veritabanını ve imza güncellemelerini başlat
        dbHelper.initializeDatabase()
        UpdateManager.checkForUpdates(this)
    }
}
