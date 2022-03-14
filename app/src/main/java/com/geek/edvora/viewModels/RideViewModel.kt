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
import java.util.*

class RideViewModel(
    private val repo: RideRepository,
    private val tab: Int
) : ViewModel() {

    private var rideData = MutableLiveData<List<RideDataItem>>()
    var isStateFilteredApplied = false
    var isCityFilteredApplied = false
    var StateFilter = ""
    var CityFilter = ""

    init {
        GlobalScope.launch(Dispatchers.IO) {
//            repo.getUserData()
//            repo.getRideData()
        }
        validateData()
    }

    val finalRideData: LiveData<List<RideDataItem>>
        get() {
            return rideData
        }

    private fun validateData() { // sorting and filtering data according to nearest | upcoming | past
        repo.rideDataList.observeForever {tempRideData->
            if (tab == -1) {
                val temp = tempRideData.filter {
                    it.formatDate.after(
                        Date()
                    )
                }
                val sorted = temp.sortedBy {
                    it.distanceFromUser
                }
                rideData.postValue(sorted)
            }
            if (tab == 0) {
                val temp = tempRideData.filter {
                    it.formatDate.after(Date())
                }
                rideData.postValue(temp)
            }
            if (tab == 1) {
                val temp = tempRideData.filter {
                    it.formatDate.before(Date())
                }
                rideData.postValue(temp)

            }
        }
    }


    val userData: LiveData<UserData>
        get() {
            return repo.userData
        }
}

@Suppress("UNCHECKED_CAST")
class RideViewModelFactory(private val repo: RideRepository, private val tab: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RideViewModel(repo, tab) as T
    }

}