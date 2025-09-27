class DashboardFragment : Fragment() {
    
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDashboard()
        loadSecurityOverview()
    }
    
    private fun setupDashboard() {
        // Security cards'ı ayarla
        binding.cardQuickScan.setOnClickListener { startQuickScan() }
        binding.cardDeepScan.setOnClickListener { startDeepScan() }
        binding.cardNetworkSecurity.setOnClickListener { openNetworkSecurity() }
        binding.cardQuarantine.setOnClickListener { openQuarantine() }
    }
    
    private fun loadSecurityOverview() {
        viewLifecycleOwner.lifecycleScope.launch {
            val overview = withContext(Dispatchers.IO) {
                SecurityOverviewGenerator.generateOverview(requireContext())
            }
            updateSecurityOverview(overview)
        }
    }
}
