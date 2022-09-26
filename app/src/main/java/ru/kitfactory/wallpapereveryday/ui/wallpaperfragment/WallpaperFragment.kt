package ru.kitfactory.wallpapereveryday.ui.wallpaperfragment

import android.app.WallpaperManager
import android.app.WallpaperManager.FLAG_LOCK
import android.app.WallpaperManager.FLAG_SYSTEM
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.kitfactory.wallpapereveryday.R
import ru.kitfactory.wallpapereveryday.databinding.FragmentWallpaperBinding


class WallpaperFragment : Fragment() {

    private val wallpaperFromArgs  by navArgs<WallpaperFragmentArgs>()
    private var _binding: FragmentWallpaperBinding? = null
    private val binding get() = _binding!!
    private var isAllFabsVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWallpaperBinding.inflate(inflater, container, false)
        val view = binding.root
        val image = binding.imageWallpaper
        val wallpaper = wallpaperFromArgs.selectedWallpaper
        Glide
            .with(view)
            .load(wallpaper.url).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
            .placeholder(R.drawable.th)
            .into(image)

        val copyright = binding.copyrightView

        copyright.text = wallpaper.copyright

        val fabApply = binding.fabApply
        val applyHomeFab = binding.applyHomeFab
        val applyLockscreenFab = binding.applyLockscreenFab
        val applyAllFab = binding.applyAllFab

        applyHomeFab.hide()
        applyLockscreenFab.hide()
        applyAllFab.hide()

        fabApply.setOnClickListener {
            isAllFabsVisible = if (!isAllFabsVisible) {
                fabApply.setImageResource(R.drawable.ic_baseline_expand_more_24)
                applyHomeFab.show()
                applyLockscreenFab.show()
                applyAllFab.show()
                true
            } else {
                fabApply.setImageResource(R.drawable.ic_baseline_expand_less_24)
                applyHomeFab.hide()
                applyLockscreenFab.hide()
                applyAllFab.hide()
                false
            }
        }

        val manager = WallpaperManager.getInstance(this.context)
        applyAllFab.setOnClickListener {
            manager.setBitmap(image.drawable.toBitmap())
        }

        applyLockscreenFab.setOnClickListener{

            manager.setBitmap(
                image.drawable.toBitmap(),
                null,
                false,
                FLAG_LOCK)
        }

        applyHomeFab.setOnClickListener{
            FLAG_SYSTEM
            manager.setBitmap(
                image.drawable.toBitmap(),
                null,
                false,
                FLAG_SYSTEM)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
