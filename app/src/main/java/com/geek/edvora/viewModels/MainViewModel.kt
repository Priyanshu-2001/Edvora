package com.geek.edvora.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geek.edvora.dataModel.RideDataItem
import com.geek.edvora.dataModel.UserData
import com.geek.edvora.repository.RideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val repo: RideRepository) :
    ViewModel(

    ) {
    val SelectedState = MutableLiveData<Int>()
    val SelectedCity = MutableLiveData<Int>()

    init {
        GlobalScope.launch(Dispatchers.Main) {
            repo.getUserData()
            repo.getRideData()
        }
    }

    val userData: LiveData<UserData>
        get() {
            return repo.userData
        }
    val finalRideData: LiveData<List<RideDataItem>>
        get() {
            return repo.rideDataList
        }
}

class MainViewModelFactory(private val repo: RideRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }

}