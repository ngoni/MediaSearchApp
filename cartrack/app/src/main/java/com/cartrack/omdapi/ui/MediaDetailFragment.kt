package com.cartrack.omdapi.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cartrack.omdapi.Constants.API_KEY_VALUE
import com.cartrack.omdapi.Constants.KEY_API_KEY
import com.cartrack.omdapi.Constants.KEY_IMDB_ID
import com.cartrack.omdapi.R
import com.cartrack.omdapi.data.entities.MediaContent
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

    private val toolBarTitleViewModel: ToolBarTitleViewModel by lazy {
        ViewModelProvider(requireActivity())[ToolBarTitleViewModel::class.java]
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
        val args: MediaDetailFragmentArgs by navArgs()
        val map: MutableMap<String, String> = mutableMapOf()
        map.apply {
            put(KEY_IMDB_ID, args.imdbId)
            put(KEY_API_KEY, API_KEY_VALUE)
        }
        viewModel.getMediaDetail(args.imdbId, map)
    }

    private fun observeViewModel() {
        viewModel.media?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    hideProgressBar(binding.progressBar)
                    binding.apply {
                        mediaDetailContainer.visibility =
                            if (it.data != null) View.VISIBLE else View.GONE
                        resultsState.visibility = if (it.data == null) View.VISIBLE else View.GONE
                    }
                    bindDataToUi(it.data)
                }
                Resource.Status.ERROR -> {
                    hideProgressBar(binding.progressBar)
                    binding.resultsState.apply {
                        text = it.message
                        visibility = View.VISIBLE
                    }
                }
                Resource.Status.LOADING -> {
                    binding.apply {
                        mediaDetailContainer.visibility = View.GONE
                        resultsState.visibility = View.GONE
                    }
                    showProgressBar(binding.progressBar)
                }
            }
        })
    }

    private fun bindDataToUi(data: MediaContent?) {
        data?.let {
            binding.apply {
                imdbRating.text = data.imdbRating
                metascoreRating.text = data.metascore
                releaseDate.text =
                    String.format(getString(R.string.release_date_text), data.released)
                director.text = String.format(getString(R.string.director_text), data.director)
                writer.text = String.format(getString(R.string.writers_text), data.writer)
                storylineDescription.text = data.plot

                Glide.with(requireContext())
                    .load(data.poster)
                    .placeholder(ColorDrawable(Color.GRAY))
                    .into(mediaPosterImage)

                toolBarTitleViewModel.setToolbarTitle(data.title)
            }
        }
    }
}