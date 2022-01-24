package br.touchetime.ui.createfight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.data.model.Fight
import br.touchetime.databinding.FragmentCreateFightBinding
import br.touchetime.ui.categoryfragment.CategoryFragment
import br.touchetime.ui.homefragment.HomeFragment
import br.touchetime.ui.stylefragment.StyleFragment
import br.touchetime.ui.weightfragment.WeightFragment

class CreateFightFragment : Fragment() {

    private lateinit var viewBinding: FragmentCreateFightBinding
    private val viewModel: CreateFightViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    private val resultKeys = arrayOf(
        CategoryFragment.CATEGORY_SELECTED,
        CategoryFragment.VIEW_CATEGORY,
        StyleFragment.STYLE_SELECTED,
        StyleFragment.VIEW_STYLE_SELECTED,
        WeightFragment.WEIGHT_SELECTED,
        WeightFragment.VIEW_WEIGHT_SELECTED
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentCreateFightBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategory()
        setupStyle()
        setupWeight()
        setupFragmentResultListeners()
        setupBack()
    }

    private fun setupCategory() {
        viewBinding.category.apply {
            setTitle(getString(R.string.category_title))
            setDescription(getString(R.string.category_description))
            setIconVisibility(false)

            setOnClickListener {
                viewBinding.apply {
                    style.setComponentEnabled(true)
                    style.isEnabled = true
                    textStyle.isEnabled = true
                }

                CategoryFragment.show(
                    childFragmentManager,
                    viewModel.getFight()
                )
            }
        }
    }

    private fun setupStyle() {
        viewBinding.style.apply {
            setTitle(getString(R.string.style_title))
            setDescription(getString(R.string.style_description))
            setIconVisibility(false)
            setComponentEnabled(false)
            isEnabled = false

            setOnClickListener {
                if (isEnabled) {
                    viewBinding.apply {
                        weight.setComponentEnabled(true)
                        weight.isEnabled = true
                        textWeight.isEnabled = true
                    }

                    StyleFragment.show(
                        childFragmentManager,
                        viewModel.getFight()
                    )
                }
            }
        }
    }

    private fun setupWeight() {
        viewBinding.weight.apply {
            setTitle(getString(R.string.weight_title))
            setDescription(getString(R.string.weight_description))
            setIconVisibility(false)
            setComponentEnabled(false)
            isEnabled = false

            setOnClickListener {
                if (isEnabled) {
                    WeightFragment.show(
                        childFragmentManager,
                        viewModel.getFight()
                    )
                }
            }
        }
    }

    private fun setupFragmentResultListeners() {
        resultKeys.forEach {
            childFragmentManager.setFragmentResultListener(
                it,
                viewLifecycleOwner,
                ::handleFragmentResult
            )
        }
    }

    private fun setupBack() {
        viewBinding.back.setOnClickListener {
            mainActivity?.navigateToFragment(
                HomeFragment.newInstance(),
                HomeFragment.TAG
            )
        }
    }

    private fun handleFragmentResult(key: String, bundle: Bundle) {
        when (key) {
            CategoryFragment.CATEGORY_SELECTED -> {
                bundle.getParcelable<Fight>(CategoryFragment.CATEGORY)?.let { fight ->
                    fight.category?.let {
                        viewModel.setCategory(it)
                    }
                }
            }
            CategoryFragment.VIEW_CATEGORY -> {
                viewCategorySelected()
            }
            StyleFragment.STYLE_SELECTED -> {
                bundle.getParcelable<Fight>(StyleFragment.FIGHT)?.let { fight ->
                    fight.style?.let {
                        viewModel.setStyle(it)
                    }
                }
            }
            StyleFragment.VIEW_STYLE_SELECTED -> {
                viewStyleSelected()
            }
            WeightFragment.WEIGHT_SELECTED -> {
                bundle.getParcelable<Fight>(WeightFragment.FIGHT)?.let { fight ->
                    fight.weight?.let {
                        viewModel.setWeight(it)
                    }
                }
            }
            WeightFragment.VIEW_WEIGHT_SELECTED -> {
                viewWeightSelected()
            }
        }
    }

    private fun viewCategorySelected() {
        viewBinding.apply {
            category.visibility = View.INVISIBLE

            categorySelected.apply {
                text = viewModel.getFight()?.category
                visibility = View.VISIBLE
            }
        }
    }

    private fun viewStyleSelected() {
        viewBinding.apply {
            style.visibility = View.INVISIBLE

            styleSelected.apply {
                text = viewModel.getFight()?.style
                visibility = View.VISIBLE
            }
        }
    }

    private fun viewWeightSelected() {
        viewBinding.apply {
            weight.visibility = View.INVISIBLE

            weightSelected.apply {
                text = viewModel.getFight()?.weight.toString()
                visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.createfight"

        fun newInstance() = CreateFightFragment()
    }
}
