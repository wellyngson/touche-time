package br.touchetime.ui.categoryfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.touchetime.data.model.Fight
import br.touchetime.databinding.FragmentCategoryBinding
import br.touchetime.ui.bottomcontrol.BottomSheetDialogTransparentBackgroundFragment

class CategoryFragment : BottomSheetDialogTransparentBackgroundFragment() {

    private lateinit var viewBinding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentCategoryBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readArgs()
        setupAdapter()
        setupChooseCategory()
    }

    private fun readArgs() {
        (arguments?.getParcelable(ARG_FIGHT) as? Fight)?.let {
            viewModel.setFight(it)
        }
    }

    private fun setupAdapter() {
        viewBinding.category.adapter = context?.let {
            viewModel.getListCategory(it)
        }?.let { listCategory ->
            CategoryAdapter(
                listCategory
            ) { category ->
                onCategorySelected(getString(category))
            }
        }
    }

    private fun setupChooseCategory() {
        viewBinding.finish.setOnClickListener {
            onCategorySelectedResult()
        }
    }

    private fun onCategorySelected(category: String) {
        viewModel.setCategorySelected(category)
    }

    private fun onCategorySelectedResult() {
        parentFragmentManager.setFragmentResult(
            CATEGORY_SELECTED,
            bundleOf(
                FIGHT to viewModel.getFight()
            )
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.categoryfragment"
        const val FIGHT = "FIGHT"
        const val CATEGORY_SELECTED = "CATEGORY_SELECTED"
        private const val ARG_FIGHT = "ARG_FIGHT"

        private fun newInstance(fight: Fight?) = CategoryFragment().apply {
            arguments = bundleOf(
                ARG_FIGHT to fight
            )
        }

        fun show(fragmentManager: FragmentManager, fight: Fight?) =
            newInstance(fight).show(fragmentManager, TAG)
    }
}
