package ru.kitfactory.wallpapereveryday.ui.settingsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.kitfactory.wallpapereveryday.App
import ru.kitfactory.wallpapereveryday.databinding.FragmentSettingsBinding
import ru.kitfactory.wallpapereveryday.di.factory.ViewModelFactory
import ru.kitfactory.wallpapereveryday.viewmodels.SettingsViewModel
import javax.inject.Inject

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory : ViewModelFactory
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, vmFactory)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.topAppBarSettings.setNavigationOnClickListener {
            val directions = SettingsFragmentDirections
                .actionSettingsFragmentToListWallpaperFragment()
            findNavController().navigate(directions)
        }

        binding.installWallpaperSwitch.isChecked = viewModel.loadUpdateWallpaperSettings()
        binding.installWallpaperSwitch.setOnCheckedChangeListener{buttonView, isChecked ->
            viewModel.setUpdateWallpaperSettings(buttonView.isChecked)
        }
        binding.delateWallpaperSwitch.isChecked = viewModel.loadDeleteOldWallpaperSettings()
        binding.delateWallpaperSwitch.setOnCheckedChangeListener{buttonView, isChecked ->
            viewModel.setDeleteOldWallpaperSettings(buttonView.isChecked)
        }

        binding.clearCacheButton.setOnClickListener {
            viewModel.deleteOld()
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDagger()
    }


    private fun injectDagger() {
        App.instance.appComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



