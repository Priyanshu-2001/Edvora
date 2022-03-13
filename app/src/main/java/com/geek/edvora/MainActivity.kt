package com.geek.edvora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.geek.edvora.adapter.MainFragmentPagerAdapter
import com.geek.edvora.databinding.ActivityMainBinding
import com.geek.edvora.utils.RideApplication
import com.geek.edvora.viewModels.MainViewModel
import com.geek.edvora.viewModels.MainViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val repository = (application as RideApplication).RideRepo
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]
        viewModel.userData.observe(this){
            binding.toolbar.textView.text = it.name
            Glide.with(this)
                .load(it.url)
                .into(binding.toolbar.profileImage)
        }
        
        val adapter = MainFragmentPagerAdapter(supportFragmentManager,binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position, true)
                if (tab.position == 0 || tab.position == 1 || tab.position == 3) adapter.notifyDataSetChanged()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))
    }
}