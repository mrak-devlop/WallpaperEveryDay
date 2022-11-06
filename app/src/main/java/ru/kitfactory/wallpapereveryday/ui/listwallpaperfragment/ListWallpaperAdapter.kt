package ru.kitfactory.wallpapereveryday.ui.listwallpaperfragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import ru.kitfactory.wallpapereveryday.databinding.ItemForListWallpaperBinding
import ru.kitfactory.wallpapereveryday.domain.Wallpaper
import ru.kitfactory.wallpapereveryday.utility.InternetConnection
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
        val progressBar: ProgressBar = holder.binding.progresBar
        Glide
            .with(holder.binding.root)
            .load(wallpaper.url)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?,
                                             model: Any?,
                                             target: com.bumptech.glide.request.target.Target<Drawable>?,
                                             dataSource: DataSource?,
                                             isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: com.bumptech.glide.request.target.Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .apply(RequestOptions().override(400, 600))
            .centerCrop()
            .placeholder(ColorDrawable(Color.BLACK))
            .transition(withCrossFade())
            .into(image)
        image.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                image to "wallpaper_transition"
            )
            val directions = ListWallpaperFragmentDirections
                .actionListWallpaperFragmentToWallpaperFragment(wallpaper)
            holder.itemView.findNavController().navigate(directions, extras)
        }
    }

    override fun getItemCount() = listWallpaper.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(item: List<Wallpaper>) {
        this.listWallpaper = item
        notifyDataSetChanged()
    }


}

