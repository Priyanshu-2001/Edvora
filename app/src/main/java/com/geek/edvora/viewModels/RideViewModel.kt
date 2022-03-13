package com.geek.edvora.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geek.edvora.dataModel.RideData
import com.geek.edvora.dataModel.RideDataItem
import com.geek.edvora.dataModel.UserData
import com.geek.edvora.repository.RideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RideViewModel(private val repo : RideRepository,
private val tab : Int) : ViewModel(){

    init {
        GlobalScope.launch(Dispatchers.IO) {
            repo.getUserData()
            repo.getRideData(tab)
        }
    }
    val rideData : LiveData<List<RideDataItem>>
        get() {
            return repo.rideDataList
        }
    val userData : LiveData<UserData>
        get() {
            return repo._userData
        }
}
class RideViewModelFactory(private val repo : RideRepository , private val tab : Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RideViewModel(repo,tab) as T
    }

}