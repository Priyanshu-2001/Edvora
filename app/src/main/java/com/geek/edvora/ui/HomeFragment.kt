package com.geek.edvora.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geek.edvora.MainActivity
import com.geek.edvora.R
import com.geek.edvora.adapter.MainRCVAdapter
import com.geek.edvora.dataModel.RideDataItem
import com.geek.edvora.dataModel.UserData
import com.geek.edvora.databinding.FragmentHomeBinding
import com.geek.edvora.interfaceUtils.FilterBtnInterface
import com.geek.edvora.interfaceUtils.FilterBtnInterface0
import com.geek.edvora.interfaceUtils.FilterBtnInterface1
import com.geek.edvora.utils.MyApplication
import com.geek.edvora.viewModels.RideViewModel
import com.geek.edvora.viewModels.RideViewModelFactory

private const val ARG_PARAM1 = "param1"

class HomeFragment : Fragment(R.layout.fragment_home), FilterBtnInterface, FilterBtnInterface1,
    FilterBtnInterface0 {
    private var tab: Int? = null
    private lateinit var adapter: MainRCVAdapter
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: RideViewModel
    lateinit var finalRideData: List<RideDataItem>
    private lateinit var UserDetailData: UserData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tab = it.getInt(ARG_PARAM1)
        }
        activity?.let {
            if (tab == 0)
                (it as MainActivity).setInterfaceListener0(this)
            if (tab == 1)
                (it as MainActivity).setInterfaceListener1(this)
            if (tab == -1)
                (it as MainActivity).setInterfaceListener(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressCircular.visibility = View.VISIBLE
        val repository = (requireActivity().application as MyApplication).studentRepo
        viewModel = ViewModelProvider(this, RideViewModelFactory(repository, tab = tab!!))[RideViewModel::class.java]
        viewModel.finalRideData.observe(viewLifecycleOwner) {
            viewModel.userData.observe(viewLifecycleOwner) { userData ->
                finalRideData = it
                UserDetailData = userData
                binding.progressCircular.visibility = View.GONE
                adapter = MainRCVAdapter(
                    it,
                    userData
                )
                binding.MainRCV.adapter = adapter
            }
        }
        if (viewModel.isStateFilteredApplied) {
            Toast.makeText(context, "$viewModel.StateFilter", Toast.LENGTH_SHORT).show()
            if (viewModel.StateFilter != "State") {
                viewModel.isStateFilteredApplied = true
                binding.progressCircular.visibility = View.GONE
                val temp = finalRideData.filter { it.state == viewModel.StateFilter }
                Log.e("TAG23", "onViewCreated: $temp")
                adapter.applyFilter(temp)
            } else {
                viewModel.isStateFilteredApplied = false
                adapter.applyFilter(finalRideData)
            }
            binding.progressCircular.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }
        if (viewModel.isCityFilteredApplied) {
            binding.progressCircular.visibility = View.VISIBLE
            if (viewModel.CityFilter != "City") {
                viewModel.isCityFilteredApplied = true
                binding.MainRCV.invalidate()
                val temp = finalRideData.filter { it.city.trim() == viewModel.CityFilter.trim() }
                Log.e("TAG", "applyFilterCity: $finalRideData}")
                Log.e("TAG", "applyFilterCity: $temp}")
                Log.e("TAG", "applyFilterCity: ${viewModel.CityFilter}}")
                adapter.applyFilter(temp)
            } else {
                viewModel.isCityFilteredApplied = false
                adapter.applyFilter(finalRideData)
                Log.e("TAG", "applyFilterCity: $}")
            }
            binding.progressCircular.visibility = View.GONE
            adapter.notifyDataSetChanged()
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

    override fun applyFilterState(state: String) {
        if (tab == 0)
            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
        if (state != "State") {
            viewModel.isStateFilteredApplied = true
            binding.progressCircular.visibility = View.GONE
            val temp = finalRideData.filter { it.state.trim() == state.trim() }
            adapter.applyFilter(temp)
        } else {
            viewModel.isStateFilteredApplied = false
            adapter.applyFilter(finalRideData)
        }
        binding.progressCircular.visibility = View.GONE
        adapter.notifyDataSetChanged()

    }

    override fun applyFilterCity(city: String) {
        binding.progressCircular.visibility = View.VISIBLE
        if (city != "City") {
            viewModel.isCityFilteredApplied = true
            binding.MainRCV.invalidate()
            val temp = finalRideData.filter { it.city.trim() == city.trim() }
            adapter.applyFilter(temp)
        } else {
            viewModel.isCityFilteredApplied = false
            adapter.applyFilter(finalRideData)
            Log.e("TAG", "applyFilterCity: $}")
        }
        binding.progressCircular.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    override fun applyFilterState1(state: String) {
        if (tab == 0)
            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
        if (state != "State") {
            viewModel.isStateFilteredApplied = true
            binding.progressCircular.visibility = View.GONE
            val temp = finalRideData.filter { it.state.trim() == state.trim() }
            adapter.applyFilter(temp)
        } else {
            viewModel.isStateFilteredApplied = false
            adapter.applyFilter(finalRideData)
        }
        binding.progressCircular.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    override fun applyFilterCity1(city: String) {
        binding.progressCircular.visibility = View.VISIBLE
        if (city != "City") {
            viewModel.isCityFilteredApplied = true
            binding.MainRCV.invalidate()
            val temp = finalRideData.filter { it.city.trim() == city.trim() }
            adapter.applyFilter(temp)
        } else {
            viewModel.isCityFilteredApplied = false
            adapter.applyFilter(finalRideData)
            Log.e("TAG", "applyFilterCity: $}")
        }
        binding.progressCircular.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    override fun applyFilterState0(state: String) {
        if (tab == 0)
            Toast.makeText(context, state, Toast.LENGTH_SHORT).show()
        if (state != "State") {
            viewModel.isStateFilteredApplied = true
            binding.progressCircular.visibility = View.GONE
            val temp = finalRideData.filter { it.state.trim() == state.trim() }
            adapter.applyFilter(temp)
        } else {
            viewModel.isStateFilteredApplied = false
            adapter.applyFilter(finalRideData)
        }
        binding.progressCircular.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    override fun applyFilterCity0(city: String) {
        binding.progressCircular.visibility = View.VISIBLE
        if (city != "City") {
            viewModel.isCityFilteredApplied = true
            binding.MainRCV.invalidate()
            val temp = finalRideData.filter { it.city.trim() == city.trim() }
            adapter.applyFilter(temp)
        } else {
            viewModel.isCityFilteredApplied = false
            adapter.applyFilter(finalRideData)
            Log.e("TAG", "applyFilterCity: $}")
        }
        binding.progressCircular.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }
}