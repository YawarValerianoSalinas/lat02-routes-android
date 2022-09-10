package com.jalasoft.routesapp.ui.routes.adapters

import android.annotation.SuppressLint
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jalasoft.routesapp.R
import com.jalasoft.routesapp.data.model.remote.AvailableTransport
import com.jalasoft.routesapp.data.model.remote.LinePath
import com.jalasoft.routesapp.databinding.PossibleRouteItemBinding

class PossibleRouteAdapter (var possibleRoutesList: MutableList<AvailableTransport>, val listener: IPossibleRouteListener) : RecyclerView.Adapter<PossibleRouteAdapter.PossibleRouteViewHolder>() {

    private var lastSelectedPosition = -1

    interface IPossibleRouteListener {
        fun onCityTap(possibleRoute: AvailableTransport)
    }

    class PossibleRouteViewHolder(val binding: PossibleRouteItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PossibleRouteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PossibleRouteItemBinding.inflate(inflater, parent, false)
        return PossibleRouteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PossibleRouteViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val possibleRoute = possibleRoutesList[position]
        if (position == 0) holder.binding.recommendedText.visibility = View.VISIBLE
        else holder.binding.recommendedText.visibility = View.GONE
        holder.binding.lineNameText.text = possibleRoute.transports[0].name
        val primaryColor = ContextCompat.getColor(holder.binding.root.context, R.color.color_primary_gradient)
        if (lastSelectedPosition == position) {
            holder.binding.lineNameText.setTextColor(WHITE)
            holder.binding.recommendedText.setTextColor(WHITE)
            holder.binding.transportImage.setColorFilter(WHITE)
            holder.binding.transportImage.setBackgroundColor(TRANSPARENT)
            holder.binding.transportImage.load(possibleRoute.transports[0].icons.blackUrl)
            holder.binding.mainContainer.setBackgroundColor(primaryColor)
        } else {
            holder.binding.lineNameText.setTextColor(BLACK)
            holder.binding.recommendedText.setTextColor(BLACK)
            holder.binding.transportImage.setBackgroundColor(WHITE)
            holder.binding.transportImage.setColorFilter(BLACK)
            holder.binding.transportImage.load(possibleRoute.transports.last().icons.whiteUrl)
            holder.binding.mainContainer.setBackgroundColor(WHITE)
        }
//        val imageLoader = ImageLoader.Builder(holder.binding.root.context)
//            .components {
//                add(SvgDecoder.Factory())
//            }
//            .build()
//        val request = ImageRequest.Builder(holder.binding.root.context)
//            .crossfade(true)
//            .crossfade(500)
//            .data(line.categoryRef)
//            .target(holder.binding.transportmage)
//            .build()
//
//        imageLoader.enqueue(request)
        holder.binding.possibleRouteContainer.setOnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            listener.onCityTap(possibleRoute)
        }
    }

    override fun getItemCount(): Int {
        return possibleRoutesList.size
    }

    fun updateList(linesList: MutableList<AvailableTransport>) {
        val oldSize = this.possibleRoutesList.size
        this.possibleRoutesList = linesList
        if (linesList.size <= oldSize) {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, linesList.size)
        } else {
            notifyItemRangeChanged(0, this.possibleRoutesList.size)
        }
    }

}
