package com.geek.edvora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geek.edvora.R
import com.geek.edvora.dataModel.RideDataItem
import com.geek.edvora.dataModel.UserData
import com.geek.edvora.databinding.SinlgeMainRideRcvBinding
import kotlin.math.absoluteValue

class MainRCVAdapter(private var RideData: List<RideDataItem>, private val userData: UserData) :
    RecyclerView.Adapter<MainRCVAdapter.RideViewHolder>() {

    class RideViewHolder(private val bind: SinlgeMainRideRcvBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding: SinlgeMainRideRcvBinding = bind
    }

    fun applyFilter(RideData: List<RideDataItem>) {
        this.RideData = RideData
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val binding: SinlgeMainRideRcvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.sinlge_main_ride_rcv, parent, false
        )
        return RideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        holder.binding.datamodel = RideData[position]
        holder.binding.distance.text =
            getMinDistanceFrom(RideData[position].station_path, userData.station_code).toString()
        Glide.with(holder.binding.root.context)
            .load(RideData[position].map_url)
            .into(holder.binding.imageView2)
    }

    override fun getItemCount() = RideData.size

    companion object{
        @JvmStatic
        fun getMinDistanceFrom(
            arr: List<Int>,
            target: Int
        ): Int { //Function to get Min Distance between user and ride station list
            var min = Integer.MAX_VALUE
            arr.forEach {
                min = min.coerceAtMost((it - target).absoluteValue)
            }
            return min
        }

    }

}