package com.cartrack.omdapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cartrack.omdapi.Constants
import com.cartrack.omdapi.utils.ViewUtils.hideProgressBar
import com.cartrack.omdapi.utils.ViewUtils.showProgressBar
import com.cartrack.omdapi.databinding.FragmentMediaListBinding
import com.cartrack.omdapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaListFragment : Fragment() {

    private lateinit var binding: FragmentMediaListBinding

    private val viewModel: MediaViewModel by lazy {
        ViewModelProvider(this)[MediaViewModel::class.java]
    }
    private lateinit var adapter: MediaListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaListBinding.inflate(inflater, container, false)

        adapter = MediaListAdapter()
        binding.recyclerview.adapter = adapter

        searchMedia()
        observeViewModel()

        return binding.root
    }

    private fun searchMedia() {
        val map: MutableMap<String, String> = mutableMapOf()
        map["s"] = "fire"
        map["type"] = "movie, series"
        map["apikey"] = Constants.API_KEY
        viewModel.searchMedia(map)
    }

    private fun observeViewModel() {
        viewModel.mediaList?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(context, "${it.data}", Toast.LENGTH_SHORT).show()
                    hideProgressBar(binding.progressBar)
                    adapter.setData(it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                    hideProgressBar(binding.progressBar)
                }
                Resource.Status.LOADING -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    showProgressBar(binding.progressBar)
                }
            }
        })
    }
}