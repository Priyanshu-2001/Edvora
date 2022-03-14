package com.geek.edvora.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.geek.edvora.R
import com.geek.edvora.Service.RideAPI
import com.geek.edvora.databinding.FilterWindowBinding
import com.geek.edvora.interfaceUtils.FilterBtnInterface
import com.geek.edvora.repository.RideRepository
import com.geek.edvora.utils.RetrofitHelper
import com.geek.edvora.viewModels.MainViewModel
import com.geek.edvora.viewModels.MainViewModelFactory


class FilterDialog : DialogFragment(R.layout.filter_window), FilterBtnInterface {

    lateinit var binding: FilterWindowBinding
    lateinit var viewModel: MainViewModel
    lateinit var stateList: List<String>
    lateinit var cityList: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FilterWindowBinding.bind(view)
        dialog!!.window!!.setGravity(Gravity.END or Gravity.TOP)
        val params: WindowManager.LayoutParams = dialog!!.window!!.attributes
        params.y = 250
        dialog!!.window!!.attributes = params
        val service = RetrofitHelper().getInstance().create(RideAPI::class.java)
        val repository = RideRepository(service, requireContext())
        viewModel =
            ViewModelProvider(
                requireActivity(),
                MainViewModelFactory(repository)
            )[MainViewModel::class.java]

        viewModel.finalRideData.observe(requireActivity()) {
            try {
                stateList = it[0].stateList.sorted().toMutableList()
                cityList = it[0].cityList.sorted().toMutableList()
                (cityList as MutableList<String>).add(0, "City")
                (stateList as MutableList<String>).add(0, "State")
                binding.citySpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cityList)
                binding.stateSpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, stateList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.SelectedCity.let {
                    if (it.value != position) {
                        Log.e("TAG onViewCreated", "onItemSelected: ${it.value} , $position")
                        it.value = position
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }
        binding.stateSpinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.SelectedState.let {
                    if (it.value != position) {
                        Log.e("TAG onViewCreated", "onItemSelected: ${it.value} , $position")
                        it.value = position
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        viewModel.SelectedState.value?.let { binding.stateSpinner.setSelection(it) }
        viewModel.SelectedCity.value?.let { binding.citySpinner.setSelection(it) }
        viewModel.SelectedCity.observe(requireActivity()) {

        }
        viewModel.SelectedCity.observe(requireActivity()) {

        }

    }

    override fun applyFilterState(state: String) {
        TODO("Not yet implemented")
    }

    override fun applyFilterCity(city: String) {
        TODO("Not yet implemented")
    }


}
