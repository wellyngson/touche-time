package br.touchetime.ui.createfight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.data.model.Fight
import br.touchetime.data.model.UiStateFight
import br.touchetime.databinding.FragmentCreateFightBinding
import br.touchetime.ui.categoryfragment.CategoryFragment
import br.touchetime.ui.homefragment.HomeFragment
import br.touchetime.ui.stylefragment.StyleFragment
import br.touchetime.ui.weightfragment.WeightFragment
import kotlinx.coroutines.flow.collect

class CreateFightFragment : Fragment() {

    private lateinit var viewBinding: FragmentCreateFightBinding
    private val viewModel: CreateFightViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    private val resultKeys = arrayOf(
        CategoryFragment.CATEGORY_SELECTED_FIGHT,
        CategoryFragment.VIEW_CATEGORY,
        StyleFragment.STYLE_SELECTED_FIGHT,
        StyleFragment.VIEW_STYLE_SELECTED,
        WeightFragment.WEIGHT_SELECTED_FIGHT,
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
        setupObservers()
        setupFragmentResultListeners()
        setupBack()
    }

    private fun setupCategory() {
        viewBinding.containerCategory.apply {
            viewBinding.category.apply {
                setTitle(getString(R.string.category_title))
                setDescription(getString(R.string.category_description))
                setIconVisibility(false)
            }

            setOnClickListener {
                deselectedStyle()
                setupStateStyle(false)
                setupStateWeight(false)

                CategoryFragment.show(
                    childFragmentManager,
                    viewModel.getFight()
                )
            }
        }
    }

    private fun setupStyle() {
        viewBinding.containerStyle.apply {
            viewBinding.style.apply {
                setTitle(getString(R.string.style_title))
                setDescription(getString(R.string.style_description))
                setIconVisibility(false)
                setComponentEnabled(false)
                isEnabled = false
            }

            setOnClickListener {
                deselectedWeight()
                setupStateWeight(false)

                if (viewBinding.style.isEnabled) {
                    StyleFragment.show(
                        childFragmentManager,
                        viewModel.getFight()
                    )
                }
            }
        }
    }

    private fun setupWeight() {
        viewBinding.containerWeight.apply {
            viewBinding.weight.apply {
                setTitle(getString(R.string.weight_title))
                setDescription(getString(R.string.weight_description))
                setIconVisibility(false)
                setComponentEnabled(false)
                isEnabled = false
            }

            setOnClickListener {
                if (viewBinding.weight.isEnabled) {
                    WeightFragment.show(
                        childFragmentManager,
                        viewModel.getFight()
                    )
                }
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.fightStateFlow.collect {
                when (it.state) {
                    UiStateFight.Changed -> {
                        setupStateStyle(true)
                    }
                }

                if (it.style != "") {
                    setupStateWeight(true)
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

    private fun setupStateStyle(condition: Boolean) {
        viewBinding.apply {
            style.setComponentEnabled(condition)
            style.isEnabled = condition
            textStyle.isEnabled = condition
        }
    }

    private fun deselectedStyle() {
        viewBinding.apply {
            style.visibility = View.VISIBLE
            styleSelected.visibility = View.GONE
        }

        deselectedWeight()
    }

    private fun setupStateWeight(condition: Boolean) {
        viewBinding.apply {
            weight.setComponentEnabled(condition)
            weight.isEnabled = condition
            textWeight.isEnabled = condition
        }
    }

    private fun deselectedWeight() {
        viewBinding.apply {
            weight.visibility = View.VISIBLE
            weightSelected.visibility = View.GONE
        }

        setupStateWeight(false)
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
            CategoryFragment.CATEGORY_SELECTED_FIGHT -> {
                bundle.getParcelable<Fight>(CategoryFragment.CATEGORY)?.let { fight ->
                    fight.category?.let {
                        viewModel.setCategory(it)
                    }
                }
            }
            CategoryFragment.VIEW_CATEGORY -> {
                viewCategorySelected()
            }
            StyleFragment.STYLE_SELECTED_FIGHT -> {
                bundle.getParcelable<Fight>(StyleFragment.FIGHT)?.let { fight ->
                    fight.style?.let {
                        viewModel.setStyle(it)
                    }
                }
            }
            StyleFragment.VIEW_STYLE_SELECTED -> {
                viewStyleSelected()
            }
            WeightFragment.WEIGHT_SELECTED_FIGHT -> {
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
