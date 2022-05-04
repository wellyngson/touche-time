package br.touchetime.ui.createathlete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.touchetime.MainActivity
import br.touchetime.data.model.Athlete
import br.touchetime.databinding.FragmentCreateAthleteBinding
import br.touchetime.ui.categoryfragment.CategoryFragment
import br.touchetime.ui.chooseathlete.ChooseAthleteFragment
import br.touchetime.ui.stylefragment.StyleFragment
import br.touchetime.ui.weightfragment.WeightFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAthleteFragment : Fragment() {

    private lateinit var viewBinding: FragmentCreateAthleteBinding
    private val viewModel: CreateAthleteViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity
    private val resultKeys = arrayOf(
        CategoryFragment.CATEGORY_SELECTED,
        StyleFragment.STYLE_SELECTED,
        WeightFragment.WEIGHT_SELECTED
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentCreateAthleteBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupResultKeysListeners()
        setupCreateAthlete()
        setupCategory()
        setupStyle()
        setupWeight()
    }

    private fun setupResultKeysListeners() {
        resultKeys.forEach {
            when (it) {
                CategoryFragment.CATEGORY_SELECTED,
                StyleFragment.STYLE_SELECTED,
                WeightFragment.WEIGHT_SELECTED,
                -> childFragmentManager
                else -> activity?.supportFragmentManager
            }?.setFragmentResultListener(
                it,
                viewLifecycleOwner,
                ::handleResultKey
            )
        }
    }

    private fun handleResultKey(key: String, bundle: Bundle) {
        when (key) {
            CategoryFragment.CATEGORY_SELECTED -> selectCategory(bundle)
            StyleFragment.STYLE_SELECTED -> selectStyle(bundle)
            WeightFragment.WEIGHT_SELECTED -> selectWeight(bundle)
        }
    }

    private fun setupCreateAthlete() {
        viewBinding.apply {
            createAthlete.setOnClickListener {
                viewModel.apply {
                    style?.let { style ->
                        category?.let { category ->
                            weight?.let { weight ->
                                createAthlete(
                                    Athlete(
                                        name = nameText.text.toString(),
                                        style = getString(style),
                                        years = getString(category),
                                        weight = weight,
                                        defeat = 0,
                                        win = 0,
                                        fight = 0,
                                    )
                                )
                            }
                        }
                    }
                }

                navigateToCreateAthlete()
            }
        }
    }

    private fun setupCategory() {
        viewBinding.categoryText.setOnClickListener {
            CategoryFragment.show(childFragmentManager)
        }
    }

    private fun setupStyle() {
        viewBinding.styleText.setOnClickListener {
            StyleFragment.show(childFragmentManager)
        }
    }

    private fun setupWeight() {
        viewBinding.weightText.setOnClickListener {
            WeightFragment.show(
                fragmentManager = childFragmentManager,
                style = viewModel.style,
                category = viewModel.category
            )
        }
    }

    private fun selectCategory(bundle: Bundle) {
        (bundle.getSerializable(CategoryFragment.CATEGORY) as? Int)?.let {
            viewModel.setCategory(it)
            viewBinding.categoryText.text =
                viewModel.category?.let { category -> getString(category) }
        }
    }

    private fun selectStyle(bundle: Bundle) {
        (bundle.getSerializable(StyleFragment.STYLE) as? Int)?.let {
            viewModel.setStyle(it)
            viewBinding.styleText.text = viewModel.style?.let { style -> getString(style) }
        }
    }

    private fun selectWeight(bundle: Bundle) {
        (bundle.getSerializable(WeightFragment.WEIGHT) as? Int)?.let {
            viewModel.setWeight(it)
            viewBinding.weightText.text = it.toString() + "KG"
        }
    }

    private fun navigateToCreateAthlete() {
        mainActivity?.navigateToFragment(
            ChooseAthleteFragment.newInstance(),
            ChooseAthleteFragment.TAG
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.createathlete"

        fun newInstance() = CreateAthleteFragment()
    }
}
