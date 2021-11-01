package com.muryno.openweather.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muryno.openweather.databinding.FragmentHomeBinding
import com.muryno.openweather.presenter.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val carbonWeightWatcherViewModel by viewModels<WeatherViewModel>()
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel = carbonWeightWatcherViewModel
            lifecycleOwner = this@HomeFragment
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}