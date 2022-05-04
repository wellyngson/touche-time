package br.touchetime.ui.createathlete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.touchetime.data.model.Athlete
import br.touchetime.databinding.FragmentCreateAthleteBinding
import br.touchetime.ui.categoryfragment.CategoryFragment
import br.touchetime.ui.stylefragment.StyleFragment
import br.touchetime.ui.weightfragment.WeightFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAthleteFragment : Fragment() {

    private lateinit var viewBinding: FragmentCreateAthleteBinding
    private val viewModel: CreateAthleteViewModel by viewModels()
    private val resultKeys = arrayOf(
        CategoryFragment.CATEGORY_SELECTED
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
                CategoryFragment.CATEGORY_SELECTED -> childFragmentManager
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
        }
    }

    private fun setupCreateAthlete() {
        viewBinding.apply {
            createAthlete.setOnClickListener {
                val athlete = Athlete(
                    name = nameText.text.toString(),
                    style = styleText.text.toString(),
                    years = categoryText.text.toString(),
                    weight = weightText.text.toString().toInt(),
                    defeat = 0,
                    win = 0,
                    fight = 0,
                )

                viewModel.createAthlete(athlete)
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
            WeightFragment.show(childFragmentManager)
        }
    }

    private fun selectCategory(bundle: Bundle) {
        (bundle.getSerializable(CategoryFragment.CATEGORY) as? Int)?.let {
            viewBinding.categoryText.text = getString(it)
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.createathlete"

        fun newInstance() = CreateAthleteFragment()
    }
}
