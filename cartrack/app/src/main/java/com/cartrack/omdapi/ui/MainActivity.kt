package com.cartrack.omdapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cartrack.omdapi.R
import com.cartrack.omdapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val toolBarTitleViewModel: ToolBarTitleViewModel by lazy {
        ViewModelProvider(this)[ToolBarTitleViewModel::class.java]
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
        observeViewModel()
    }

    private fun setupNavController() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.toolbar, navController)

        val destinationChangedListener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                toolBarTitleViewModel.setDestination(destination)

                // hide the search icon when on MediaDetailFragment
                val visible = destination.id != R.id.mediaDetailFragment
                binding.toolbar.menu.findItem(R.id.search).isVisible = visible
            }
        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    private fun observeViewModel() {
        toolBarTitleViewModel.toolbarTitle.observe(this, Observer {
            binding.toolbar.title = it
        })
    }

}