package com.prototype.urbandictionary.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prototype.urbandictionary.R

class SplashFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This sends to the HomeFragment via NavigationComponent
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            )
        }, 750)
    }
}