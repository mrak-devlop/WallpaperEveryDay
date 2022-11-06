package ru.kitfactory.wallpapereveryday.ui.listwallpaperfragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialArcMotion
import ru.kitfactory.wallpapereveryday.App
import ru.kitfactory.wallpapereveryday.R
import ru.kitfactory.wallpapereveryday.data.storage.PreferencesStorage
import ru.kitfactory.wallpapereveryday.databinding.FragmentListWallpaperBinding
import ru.kitfactory.wallpapereveryday.di.factory.ViewModelFactory
import ru.kitfactory.wallpapereveryday.utility.InternetConnection
import ru.kitfactory.wallpapereveryday.utility.ScreenGrid
import ru.kitfactory.wallpapereveryday.viewmodels.ListWallpaperViewModel
import javax.inject.Inject


class ListWallpaperFragment : Fragment() {

    private var _binding: FragmentListWallpaperBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var vmFactory: ViewModelFactory
    private val viewModel: ListWallpaperViewModel by lazy {
        ViewModelProvider(this, vmFactory)[ListWallpaperViewModel::class.java]
    }

    @Inject
    lateinit var storage: PreferencesStorage



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListWallpaperBinding.inflate(inflater, container, false)
        val view = binding.root
        val wallpaper = viewModel.localWallpaper
        val adapter = ListWallpaperAdapter()
        val recyclerView = binding.wallpaperRecyclerView
        recyclerView.adapter = adapter
        if(viewModel.checkFirstRun()){
            autoDeleteDialog(binding.root.context)
        }
        val screenGrid = ScreenGrid(this.binding.root.context, 120F).calculate()
        recyclerView.layoutManager = GridLayoutManager(this.binding.root.context, screenGrid)
        wallpaper.observe(viewLifecycleOwner) { item -> adapter.setData(item) }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toSettingsButton -> {
                    val directions = ListWallpaperFragmentDirections
                        .actionListWallpaperFragmentToSettingsFragment()
                    findNavController().navigate(directions)
                    true
                }
                else -> false
            }
        }

        if (!InternetConnection(binding.root.context).checkInternet()){
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "No Internet connection",
                Snackbar.LENGTH_LONG)
                .show()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDagger()
        exitTransition = MaterialContainerTransform().apply{
            MaterialArcMotion()
            duration = 550
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewGroup = view.parent as ViewGroup
        viewGroup
            .viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        super.onViewCreated(view, savedInstanceState)

    }


    private fun injectDagger() {
        App.instance.appComponent.inject(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Glide.get(this.binding.root.context).clearMemory()
        _binding = null
    }

    private fun autoDeleteDialog (context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.dialog_title_del))
            .setMessage(resources.getString(R.string.dialog_supporting_text_del))
            .setNeutralButton(resources.getString(R.string.dialog_cancel)) { dialog, which ->
                viewModel.setAutoDelete(false)
            }
            .setPositiveButton(resources.getString(R.string.dialog_accept)) { dialog, which ->
                viewModel.setAutoDelete(true)
            }
            .show()
    }


}