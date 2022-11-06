package ru.kitfactory.wallpapereveryday.ui.wallpaperfragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import ru.kitfactory.wallpapereveryday.App
import ru.kitfactory.wallpapereveryday.R
import ru.kitfactory.wallpapereveryday.databinding.FragmentWallpaperBinding
import ru.kitfactory.wallpapereveryday.di.factory.ViewModelFactory
import ru.kitfactory.wallpapereveryday.utility.InternetConnection
import ru.kitfactory.wallpapereveryday.utility.SetWallpaper
import ru.kitfactory.wallpapereveryday.viewmodels.WallpaperViewModel
import javax.inject.Inject


class WallpaperFragment : Fragment() {
    @Inject
    lateinit var setWallpaper: SetWallpaper

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private val viewModel: WallpaperViewModel by lazy {
        ViewModelProvider(this, vmFactory)[WallpaperViewModel::class.java]
    }
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

        binding.topAppBarWallpaper.setNavigationOnClickListener {
            val directions = WallpaperFragmentDirections
                .actionWallpaperFragmentToListWallpaperFragment()
            findNavController().navigate(directions)
        }

        binding.topAppBarWallpaper.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toInfoButton -> {
                    binding.copyrightView.toggleVisibility()
                    true
                }
                else -> false
            }

        }

        val wallpaper = wallpaperFromArgs.selectedWallpaper
        binding.topAppBarWallpaper.title = wallpaper.startDate
        val progressBar = binding.linerProgessBarWallpaper
        Glide
            .with(view)
            .load(wallpaper.url)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
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

        applyAllFab.setOnClickListener {
            val bitmap = image.drawable.toBitmap()
            viewModel.setWallpaperForAllScreen(bitmap)
            updateDialog(binding.root.context)
        }

        applyLockscreenFab.setOnClickListener {
            val bitmap = image.drawable.toBitmap()
            viewModel.setWallpaperForLockScreen(bitmap)
        }

        applyHomeFab.setOnClickListener {
            val bitmap = image.drawable.toBitmap()
            viewModel.setWallpaperForHomeScreen(bitmap)
        }

        if (!InternetConnection(binding.root.context).checkInternet()){
            val textMessage = binding.root.context.resources.getString(R.string.no_internet)
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                textMessage,
                Snackbar.LENGTH_LONG)
                .show()
        }
        
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDagger()
        sharedElementEnterTransition = MaterialContainerTransform()
        enterTransition = MaterialFadeThrough()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        Glide.get(this.binding.root.context).clearMemory()
        _binding = null
    }

    private fun injectDagger() {
        App.instance.appComponent.inject(this)
    }

    private fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    private fun updateDialog (context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialog_title))
            .setMessage(resources.getString(R.string.dialog_supporting_text))
            .setNeutralButton(resources.getString(R.string.dialog_cancel)) { dialog, which ->
                viewModel.removeAutoUpdate()
            }
            .setPositiveButton(resources.getString(R.string.dialog_accept)) { dialog, which ->
                viewModel.setAutoUpdate()
            }
            .show()
    }



}
