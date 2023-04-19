package com.davega.recetasyape.ui.maps

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.compose.maps.DetailsScreen
import com.davega.recetasyape.databinding.FragmentMapsBinding
import com.google.accompanist.themeadapter.material.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding, BaseViewModel>() {
    override val viewModel: MapsViewModel  by viewModels()

    override fun getViewBinding(): FragmentMapsBinding = FragmentMapsBinding.inflate(layoutInflater)

    private val args: MapsFragmentArgs by navArgs()

    private var latOriginFood: Double = 23232323.0
    private var lngOriginFood: Double = -122132.2
    private var nameFood: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgs()
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                // You're in Compose world!
                MdcTheme {
                    Surface {
                        DetailsScreen(
                            modifier = Modifier.systemBarsPadding(),
                            latOriginFood = latOriginFood,
                            lngOriginFood = lngOriginFood,
                            nameFood = nameFood
                        )
                    }
                }
            }
        }
    }

    private fun setupArgs(){
        latOriginFood = args.lat.toDouble()
        lngOriginFood = args.lng.toDouble()
        nameFood = args.name
    }

}

