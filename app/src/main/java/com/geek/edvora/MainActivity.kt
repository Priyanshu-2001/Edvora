package com.geek.edvora

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.geek.edvora.adapter.MainFragmentPagerAdapter
import com.geek.edvora.databinding.ActivityMainBinding
import com.geek.edvora.interfaceUtils.FilterBtnInterface
import com.geek.edvora.interfaceUtils.FilterBtnInterface0
import com.geek.edvora.interfaceUtils.FilterBtnInterface1
import com.geek.edvora.ui.dialogs.FilterDialog
import com.geek.edvora.utils.MyApplication
import com.geek.edvora.viewModels.MainViewModel
import com.geek.edvora.viewModels.MainViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var FilterBtnInterface: FilterBtnInterface
    lateinit var FilterBtnInterface0: FilterBtnInterface0
    lateinit var FilterBtnInterface1: FilterBtnInterface1
    lateinit var stateList: List<String>
    lateinit var cityList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val repository = (application as MyApplication).studentRepo
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(repository)
            )[MainViewModel::class.java]
        viewModel.userData.observe(this) {
            binding.toolbar.textView.text = it.name
            Glide.with(this)
                .load(it.url)
                .into(binding.toolbar.profileImage)
        }
        viewModel.finalRideData.observe(this) {
            try {
                stateList = it[0].stateList.sorted().toMutableList()
                cityList = it[0].cityList.sorted().toMutableList()
                (cityList as MutableList<String>).add(0, "City")
                (stateList as MutableList<String>).add(0, "State")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val adapter = MainFragmentPagerAdapter(supportFragmentManager, binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position, true)
                if (tab.position == 0 || tab.position == 1 || tab.position == 3) adapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewModel.SelectedCity.observe(this) {
            Log.e("TAG1", "onCreate: $cityList")
            it?.let { idx ->
                FilterBtnInterface.applyFilterCity(
                    cityList[idx]
                )
            }
            it?.let { idx ->
                FilterBtnInterface0.applyFilterCity0(
                    cityList[idx]
                )
            }
            it?.let { idx ->
                FilterBtnInterface1.applyFilterCity1(
                    cityList[idx]
                )
            }
        }
        viewModel.SelectedState.observe(this) {
            it?.let { idx ->
                FilterBtnInterface.applyFilterState(
                    stateList[idx]
                )
            }
            it?.let { idx ->
                FilterBtnInterface1.applyFilterState1(
                    stateList[idx]
                )
            }
            it?.let { idx ->
                FilterBtnInterface0.applyFilterState0(
                    stateList[idx]
                )
            }
        }
        binding.filterBtn.setOnClickListener {
            FilterDialog().apply {
                show(supportFragmentManager, "dialog")
            }
        }
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
    }

    fun setInterfaceListener(listener: FilterBtnInterface) {
        this.FilterBtnInterface = listener
    }

    fun setInterfaceListener0(listener: FilterBtnInterface0) {
        this.FilterBtnInterface0 = listener
    }

    fun setInterfaceListener1(listener: FilterBtnInterface1) {
        this.FilterBtnInterface1 = listener
    }

}