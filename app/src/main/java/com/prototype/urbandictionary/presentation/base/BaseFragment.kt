package com.prototype.urbandictionary.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.prototype.urbandictionary.BR

// This is a base class, this saves so much time when working with fragments
// There's also a BaseActivity class, but on this case, i will be using only the BaseFragment class

abstract class BaseFragment<Binding : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
): Fragment() {

    /**
     * This Binding variable needs to be nullable to clear it on [onDestroyView]
     * to avoid a [Memory Leak].
     */
    private var _binding: Binding? = null
    abstract val viewModel: ViewModel?

    /**
     * This Binding is only valid between [onCreateView] and [onDestroyView].
     *
     * Throws an [IllegalArgumentException] if [_binding] property is null. Otherwise
     * returns a valid Binding.
     */
    val binding: Binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        ) as Binding

        return _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel?.let { setVariable(BR.viewModel, it) }
        }?.root ?: View(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentReady()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Safe function that triggers when the [Fragment] is ready
     */
    abstract fun onFragmentReady()
}