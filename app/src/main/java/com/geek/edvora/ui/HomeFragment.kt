package com.geek.edvora.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geek.edvora.R
import com.geek.edvora.Service.RideAPI
import com.geek.edvora.adapter.MainRCVAdapter
import com.geek.edvora.databinding.FragmentHomeBinding
import com.geek.edvora.repository.RideRepository
import com.geek.edvora.utils.RetrofitHelper
import com.geek.edvora.viewModels.RideViewModel
import com.geek.edvora.viewModels.RideViewModelFactory

private const val ARG_PARAM1 = "param1"

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var tab: Int? = null
    private lateinit var adapter : MainRCVAdapter
    private lateinit var binding : FragmentHomeBinding
    lateinit var viewModel : RideViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tab = it.getInt(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressCircular.visibility = View.VISIBLE
        val service = RetrofitHelper().getInstance().create(RideAPI::class.java)
        val repository = RideRepository(service,requireContext())
        viewModel = ViewModelProvider(this, RideViewModelFactory(repository, tab = tab!!))[RideViewModel::class.java]
        viewModel.finalRideData.observe(viewLifecycleOwner) {
            viewModel.userData.observe(viewLifecycleOwner){userData->
                binding.progressCircular.visibility = View.GONE
                adapter = MainRCVAdapter(
                    it,
                    userData
                )
                binding.MainRCV.adapter = adapter
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tab: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, tab)
                }
            }
    }
}