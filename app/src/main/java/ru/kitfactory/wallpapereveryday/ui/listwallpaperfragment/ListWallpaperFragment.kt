package ru.kitfactory.wallpapereveryday.ui.listwallpaperfragment

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import ru.kitfactory.wallpapereveryday.App
import ru.kitfactory.wallpapereveryday.R
import ru.kitfactory.wallpapereveryday.data.storage.PreferencesStorage
import ru.kitfactory.wallpapereveryday.databinding.FragmentListWallpaperBinding
import ru.kitfactory.wallpapereveryday.di.factory.ViewModelFactory
import ru.kitfactory.wallpapereveryday.viewmodels.ListWallpaperViewModel
import javax.inject.Inject

class ListWallpaperFragment : Fragment() {

    private var _binding: FragmentListWallpaperBinding? = null
    private val binding get() = _binding!!
    private var myPreferences: SharedPreferences? = null

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
        if (isPortraitMode()) {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        } else {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 5)
        }
        wallpaper.observe(viewLifecycleOwner) { item -> adapter.setData(item) }
        checkFirstRun()
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
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDagger()
        myPreferences = this.activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }


    private fun injectDagger() {
        App.instance.appComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Glide.get(this.binding.root.context).clearMemory()
        _binding = null
    }

    private fun isPortraitMode(): Boolean {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> true
            else -> false
        }
    }

    private fun checkFirstRun() {
        val state = storage.getProperty("FirstRun")
        if (state == "none") {
            storage.addProperty("FirstRun", "NO")
            viewModel.loadWallpapersOnWeekInDb()
        }
    }


}