package com.cartrack.omdapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrack.omdapi.Constants.API_KEY_VALUE
import com.cartrack.omdapi.Constants.KEY_API_KEY
import com.cartrack.omdapi.Constants.KEY_SEARCH_TITLE
import com.cartrack.omdapi.Constants.KEY_TYPE
import com.cartrack.omdapi.R
import com.cartrack.omdapi.data.entities.Type
import com.cartrack.omdapi.databinding.FragmentMediaListBinding
import com.cartrack.omdapi.utils.Resource
import com.cartrack.omdapi.utils.ViewUtils.hideProgressBar
import com.cartrack.omdapi.utils.ViewUtils.showProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaListFragment : Fragment() {

    private lateinit var binding: FragmentMediaListBinding
    private lateinit var navController: NavController
    private lateinit var mediaListAdapter: MediaListAdapter

    private val viewModel: MediaViewModel by lazy {
        ViewModelProvider(this)[MediaViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaListBinding.inflate(inflater, container, false)

        setupAdapter()
        initViews()
        searchMedia()
        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun initViews() {
        binding.recyclerview.apply {
            adapter = mediaListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupAdapter() {
        val callback = fun(imdbId: String) {
            val action =
                MediaListFragmentDirections.actionOpenMediaDetailScreen(imdbId)
            navController.navigate(action)
        }
        MediaListAdapter(callback = callback).also { mediaListAdapter = it }
    }

    private fun searchMedia() {
        val map: MutableMap<String, String> = mutableMapOf()
        map.apply {
            put(KEY_SEARCH_TITLE, "fire")
            put(KEY_TYPE, Type.Movie.value)
            put(KEY_API_KEY, API_KEY_VALUE)
        }
        viewModel.searchMedia(map)
    }

    private fun observeViewModel() {
        viewModel.mediaList?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    hideProgressBar(binding.progressBar)
                    if (it.data?.isEmpty() == true) {
                        binding.resultsState.apply {
                            text = getString(R.string.no_results)
                            visibility = View.VISIBLE
                        }
                    } else {
                        mediaListAdapter.updateData(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    hideProgressBar(binding.progressBar)
                    binding.resultsState.apply {
                        text = it.message
                        visibility = View.VISIBLE
                    }
                }
                Resource.Status.LOADING -> {
                    binding.resultsState.visibility = View.GONE
                    showProgressBar(binding.progressBar)
                }
            }
        })
    }
}