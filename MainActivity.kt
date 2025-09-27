class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
    // Manager'lar
    private lateinit var scanner: VirusScanner
    private lateinit var networkSecurity: NetworkSecurity
    private lateinit var quarantineManager: QuarantineManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeManagers()
        setupNavigation()
        setupUI()
        checkPermissions()
    }
    
    private fun initializeManagers() {
        val app = application as ThunderApplication
        scanner = VirusScanner(this)
        networkSecurity = NetworkSecurity(this)
        quarantineManager = QuarantineManager
        
        // İlk taramayı başlat
        performInitialSecurityCheck()
    }
    
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        binding.bottomNavigation.setupWithNavController(navController)
    }
    
    private fun setupUI() {
        // Toolbar'ı ayarla
        setSupportActionBar(binding.toolbar)
        
        // Security status'ü güncelle
        updateSecurityStatus()
        
        // Click listener'ları ayarla
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.fabQuickScan.setOnClickListener {
            startQuickScan()
        }
        
        binding.btnEmergencyScan.setOnClickListener {
            startEmergencyScan()
        }
    }
    
    private fun startQuickScan() {
        val intent = Intent(this, ScanActivity::class.java).apply {
            putExtra("scan_type", "quick")
        }
        startActivity(intent)
    }
    
    private fun performInitialSecurityCheck() {
        CoroutineScope(Dispatchers.IO).launch {
            val securityStatus = checkOverallSecurity()
            withContext(Dispatchers.Main) {
                updateSecurityStatus(securityStatus)
            }
        }
    }
    
    private fun checkOverallSecurity(): SecurityStatus {
        return SecurityStatus(
            realTimeProtectionEnabled = isRealTimeProtectionActive(),
            lastScanDate = getLastScanDate(),
            threatsDetected = getRecentThreatCount(),
            networkSecurity = checkNetworkSecurity(),
            updateStatus = checkUpdateStatus()
        )
    }
}
