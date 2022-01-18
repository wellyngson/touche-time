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
        StyleFragment.STYLE_SELECTED,
        WeightFragment.WEIGHT_SELECTED
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

            setOnClickListener {
                StyleFragment.show(
                    childFragmentManager,
                    viewModel.getFight()
                )
            }
        }
    }

    private fun setupWeight() {
        viewBinding.weight.apply {
            setTitle(getString(R.string.weight_title))
            setDescription(getString(R.string.weight_description))
            setIconVisibility(false)

            setOnClickListener {
                WeightFragment.show(
                    childFragmentManager,
                    viewModel.getFight()
                )
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
                bundle.getParcelable<Fight>(CategoryFragment.FIGHT)?.let { fight ->
                    viewModel.setFight(fight)
                }
            }
            StyleFragment.STYLE_SELECTED -> {
                bundle.getParcelable<Fight>(StyleFragment.FIGHT)?.let { fight ->
                    viewModel.setFight(fight)
                }
            }
            WeightFragment.WEIGHT_SELECTED -> {
                bundle.getParcelable<Fight>(WeightFragment.FIGHT)?.let { fight ->
                    viewModel.setFight(fight)
                }
            }
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.createfight"

        fun newInstance() = CreateFightFragment()
    }
}
