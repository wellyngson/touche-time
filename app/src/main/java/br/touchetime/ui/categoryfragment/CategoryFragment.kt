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
    }

    private fun readArgs() {
        (arguments?.getParcelable(ARG_FIGHT) as? Fight)?.let {
            viewModel.setFight(it)
        }
    }

    private fun setupAdapter() {
        val listCategory = viewModel.getListCategory()

        viewBinding.category.adapter = CategoryAdapter(
            listCategory
        ) { position ->

            viewModel.setCategorySelected(
                getString(listCategory[position])
            )

            viewModel.getFight()?.let { fight ->
                onCategorySelected(
                    fight
                )
            }

            parentFragmentManager.setFragmentResult(VIEW_CATEGORY, bundleOf())
            dismiss()
        }
    }

    private fun onCategorySelected(fight: Fight) {
        onCategorySelectedResult(fight)
    }

    private fun onCategorySelectedResult(fight: Fight) {
        parentFragmentManager.setFragmentResult(
            CATEGORY_SELECTED,
            bundleOf(
                CATEGORY to fight
            )
        )
    }

    companion object {
        const val TAG = "br.touchetime.ui.categoryfragment"
        const val CATEGORY = "CATEGORY"
        const val CATEGORY_SELECTED = "CATEGORY_SELECTED"
        const val VIEW_CATEGORY = "VIEW_CATEGORY"
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
