package com.cartrack.omdapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cartrack.omdapi.Constants.API_KEY_VALUE
import com.cartrack.omdapi.Constants.KEY_API_KEY
import com.cartrack.omdapi.Constants.KEY_IMDB_ID
import com.cartrack.omdapi.databinding.FragmentMediaDetailBinding
import com.cartrack.omdapi.utils.Resource
import com.cartrack.omdapi.utils.ViewUtils.hideProgressBar
import com.cartrack.omdapi.utils.ViewUtils.showProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaDetailFragment : Fragment() {

    private lateinit var binding: FragmentMediaDetailBinding

    private val viewModel: MediaViewModel by lazy {
        ViewModelProvider(this)[MediaViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaDetailBinding.inflate(inflater, container, false)

        fetchMedia()
        observeViewModel()

        return binding.root
    }

    private fun fetchMedia() {
        val args : MediaDetailFragmentArgs by navArgs()
        val map: MutableMap<String, String> = mutableMapOf()
        map.apply {
            put(KEY_IMDB_ID, args.imdbId)
            put(KEY_API_KEY, API_KEY_VALUE)
        }
        viewModel.fetchMediaDetail(args.imdbId, map)
    }

    private fun observeViewModel() {
        viewModel.media?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(context, "${it.data}", Toast.LENGTH_SHORT).show()
                    hideProgressBar(binding.progressBar)
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