package com.prototype.urbandictionary.presentation.home

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.prototype.domain.model.DefinitionModel
import com.prototype.domain.model.Resource
import com.prototype.urbandictionary.R
import com.prototype.urbandictionary.databinding.HomeFragmentBinding
import com.prototype.urbandictionary.presentation.base.BaseFragment
import com.prototype.urbandictionary.presentation.util.ScreenStates
import com.prototype.urbandictionary.presentation.util.hideKeyBoard
import com.prototype.urbandictionary.presentation.util.setObserver
import com.prototype.urbandictionary.presentation.util.showKeyBoard
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    // We override the base viewModel variable by injecting it via Koin
    override val viewModel: HomeViewModel by viewModel()

    override fun onFragmentReady() {
        // Configuring the RecyclerView
        binding.rvDefinitions.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDefinitions.adapter = DefinitionsListAdapter(viewModel.definitionList)
        // Configuring the search field to be youtube like with a search button directly on the keyboard
        binding.etSearch.setOnEditorActionListener(::onEditorAction)

        // Observing some viewModel events
        viewModel.isTypingEvent.setObserver(viewLifecycleOwner, ::isSearchingEventAction)
        viewModel.definitionsData.setObserver(viewLifecycleOwner, ::wordDefinitionsDataAction)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onEditorAction(textView: TextView?, actionId: Int?, event: KeyEvent?): Boolean {
        // If the search key is pressed and the search field is not empty, the searchWord function
        // will be executed
        return if (actionId == EditorInfo.IME_ACTION_SEARCH
            && viewModel.searchText.get()?.isNotBlank() == true
        ) {
            viewModel.searchWord()
            true
        } else {
            false
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isSearchingEventAction(searching: Boolean) {
        viewModel.isTyping.set(searching)
        with(binding.etSearch) {
            if (searching) {
                // If the user is about to search
                requestFocus()
                showKeyBoard()
            } else {
                // If the user aborts the search
                clearFocus()
                hideKeyBoard()
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun wordDefinitionsDataAction(resource: Resource<List<DefinitionModel>>) {
        // We are gonna process the current status on the resource that are being served on the LiveData
        when (resource.status) {
            // Just loading, we set the screen state to loading
            Resource.Status.LOADING -> viewModel.screenState.set(ScreenStates.LOADING)
            // An error has been emitted on the LiveData
            Resource.Status.ERROR -> {
                val data = resource.data
                // We check if some data is available to show it to the user, (we can have it cached)
                if(data.isNullOrEmpty()){
                    viewModel.screenState.set(ScreenStates.DEFAULT)
                }else{
                    populateList(data)
                }

                // Data or no data available, this error will be showed to the user letting them know
                // that an error has ocurred
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.request_failed),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            // The request was a success
            Resource.Status.SUCCESS -> {
                val data = resource.data
                // We check if the result has any valid results
                if(data.isNullOrEmpty()){
                    // We set the NO_DATA screen to the user
                    viewModel.screenState.set(ScreenStates.NO_DATA)
                }else{
                    populateList(data)
                }
            }
        }
    }

    // This function is in charge to update the RecyclerView list to the last available result
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun populateList(definitions: List<DefinitionModel>){
        viewModel.screenState.set(ScreenStates.HAS_DATA)
        viewModel.definitionList.clear()
        viewModel.definitionList.addAll(definitions)
        binding.rvDefinitions.adapter?.notifyDataSetChanged()
        binding.nsvDefinitions.fullScroll(View.FOCUS_UP)
    }
}