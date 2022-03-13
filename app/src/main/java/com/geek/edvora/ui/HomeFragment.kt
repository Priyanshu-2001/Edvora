package com.geek.edvora.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.geek.edvora.R
import com.geek.edvora.adapter.MainRCVAdapter
import com.geek.edvora.databinding.FragmentHomeBinding
import com.geek.edvora.utils.RideApplication
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
        val repository = (requireActivity().application as RideApplication).RideRepo
        viewModel = ViewModelProvider(requireActivity(), RideViewModelFactory(repository, tab = tab!!))[RideViewModel::class.java]
        viewModel.rideData.observe(requireActivity()) {
            viewModel.userData.observe(requireActivity()){userData->
                adapter = MainRCVAdapter(it,
                    userData)
                binding.MainRCV.adapter = adapter
                Log.e("TAG", "onBindViewHolder: Putting in adapter $it", )

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}