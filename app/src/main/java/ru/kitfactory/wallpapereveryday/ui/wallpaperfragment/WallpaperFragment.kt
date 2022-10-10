package ru.kitfactory.wallpapereveryday.ui.wallpaperfragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.R
import ru.kitfactory.wallpapereveryday.databinding.FragmentWallpaperBinding
import ru.kitfactory.wallpapereveryday.util.SetWallpaper


class WallpaperFragment : Fragment() {

    private val wallpaperFromArgs by navArgs<WallpaperFragmentArgs>()
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
            .load(wallpaper.url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
            .placeholder(ColorDrawable(Color.BLACK))
            .transition(DrawableTransitionOptions.withCrossFade())
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

        val setWallpaper = this.context?.let { SetWallpaper(it) }
        applyAllFab.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                setWallpaper?.applyForAllScreen(image.drawable.toBitmap())
            }
        }

        applyLockscreenFab.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                setWallpaper?.applyForLockScreen(image.drawable.toBitmap())
            }
        }

        applyHomeFab.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                setWallpaper?.applyForHomeScreen(image.drawable.toBitmap())
            }
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Glide.get(this.binding.root.context).clearMemory()
        _binding = null
    }

}
