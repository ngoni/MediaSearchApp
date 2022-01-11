package com.cartrack.omdapi.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrack.omdapi.Constants.API_KEY_VALUE
import com.cartrack.omdapi.Constants.DEVICE_SETTINGS
import com.cartrack.omdapi.Constants.FIRST_TIME_LOGIN
import com.cartrack.omdapi.Constants.KEY_API_KEY
import com.cartrack.omdapi.Constants.KEY_SEARCH_TITLE
import com.cartrack.omdapi.Constants.KEY_TYPE
import com.cartrack.omdapi.R
import com.cartrack.omdapi.data.entities.Type
import com.cartrack.omdapi.databinding.FragmentMediaListBinding
import com.cartrack.omdapi.utils.Resource
import com.cartrack.omdapi.utils.ViewUtils.hideProgressBar
import com.cartrack.omdapi.utils.ViewUtils.showProgressBar
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaListFragment : Fragment() {

    private lateinit var binding: FragmentMediaListBinding
    private lateinit var navController: NavController
    private lateinit var toolbar: MaterialToolbar
    private lateinit var searchEditText: AppCompatEditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var mediaTypeFilter: String
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
        observeViewModel()
        shouldPromptUserToSearch()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initToolBar(view.rootView)
    }

    private fun initViews() {
        binding.recyclerview.apply {
            adapter = mediaListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initToolBar(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    displaySearchDialog()
                    true
                }
                else -> false
            }
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

    private fun searchMedia(searchText: String, mediaFilterType: String) {
        val map: MutableMap<String, String> = mutableMapOf()
        map.apply {
            put(KEY_SEARCH_TITLE, searchText)
            put(KEY_TYPE, mediaFilterType)
            put(KEY_API_KEY, API_KEY_VALUE)
        }
        viewModel.searchMedia(map)
    }

    private fun observeViewModel() {
        viewModel.getMediaList().observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    hideProgressBar(binding.progressBar)
                    if (it.data?.isEmpty() == true) {
                        binding.resultsState.apply {
                            text = getString(R.string.no_results)
                            visibility = View.VISIBLE
                        }
                    } else {
                        binding.resultsState.visibility = View.GONE
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

    private fun shouldPromptUserToSearch() {
        // We check to see if it's the first time a user is opening the app so we can show the Search Prompt
        val prefs = requireActivity().getSharedPreferences(DEVICE_SETTINGS, Context.MODE_PRIVATE)
        val firstTimeLogin = prefs.getBoolean(FIRST_TIME_LOGIN, true)
        if (firstTimeLogin) {
            displaySearchDialog()
        } else {
            viewModel.getPreviousSearchResults()
        }
    }

    private fun displaySearchDialog() {
        // We update the value in SharedPrefs to indicate that the user has launched the app for the first time
        requireActivity().getSharedPreferences(DEVICE_SETTINGS, Context.MODE_PRIVATE).apply {
            edit().putBoolean(FIRST_TIME_LOGIN, false).apply()
        }

        val negativeBtnListener = fun(dialog: DialogInterface, value: Int) {
            dialog.dismiss()
        }

        val positiveBtnListener = fun(dialog: DialogInterface, value: Int) {
            // Get Search text and selected Media type
            val searchText = searchEditText.text.toString().trim()
            mediaTypeFilter = setMediaTypeFilter(radioGroup.checkedRadioButtonId)

            if (searchText.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.search_hint),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                searchMedia(searchText, mediaTypeFilter)
            }
            dialog.dismiss()
        }

        val customView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.alertdialog_search_view, null)

        with(customView) {
            searchEditText = findViewById(R.id.search_edit_text)
            radioGroup = findViewById(R.id.radioGroup)
        }

        val builder: AlertDialog.Builder = with(AlertDialog.Builder(requireContext())) {
            setTitle(getString(R.string.search_content))
            setView(customView)
            setNegativeButton(getString(R.string.cancel), negativeBtnListener)
            setPositiveButton(getString(R.string.search), positiveBtnListener)
            setCancelable(true)
        }
        builder.create().show()
    }

    private fun setMediaTypeFilter(checkedId: Int): String {
        return when (checkedId) {
            R.id.movies -> {
                Type.Movie.value
            }
            R.id.series -> {
                Type.Series.value
            }
            else -> {
                Type.Movie.value
            }
        }
    }
}