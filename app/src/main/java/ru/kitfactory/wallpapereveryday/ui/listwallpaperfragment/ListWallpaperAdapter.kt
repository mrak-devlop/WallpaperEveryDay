package ru.kitfactory.wallpapereveryday.ui.listwallpaperfragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import ru.kitfactory.wallpapereveryday.R
import ru.kitfactory.wallpapereveryday.databinding.ItemForListWallpaperBinding
import ru.kitfactory.wallpapereveryday.domain.Wallpaper
import ru.kitfactory.wallpapereveryday.util.InternetConnection
import javax.inject.Inject


class ListWallpaperAdapter : RecyclerView.Adapter<ListWallpaperAdapter.ViewHolder>() {
    private var listWallpaper = emptyList<Wallpaper>()
    @Inject
    lateinit var internetConnection: InternetConnection

    inner class ViewHolder(val binding: ItemForListWallpaperBinding) : RecyclerView
    .ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForListWallpaperBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallpaper = listWallpaper[position]
        val textDate = holder.binding.dateTextView
        textDate.text = wallpaper.startDate
        val image = holder.binding.wallpaperImageView
            Glide
                .with(holder.binding.root)
                .load(wallpaper.url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(RequestOptions().override(400, 600))
                .centerCrop()
                .placeholder(R.drawable.th)
                .transition(withCrossFade())
                .into(image)
        image.setOnClickListener {
            val directions = ListWallpaperFragmentDirections
                .actionListWallpaperFragmentToWallpaperFragment(wallpaper)
            holder.itemView.findNavController().navigate(directions)
        }
    }

    override fun getItemCount() = listWallpaper.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(item: List<Wallpaper>){
        this.listWallpaper = item
        notifyDataSetChanged()
    }



}