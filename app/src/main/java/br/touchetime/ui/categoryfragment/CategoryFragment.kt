package br.touchetime.ui.categoryfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
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

        setupAdapter()
        setupChooseCategory()
    }

    private fun setupAdapter() {
        viewBinding.category.adapter = CategoryAdapter(
            viewModel.getListCategory()
        ) {
            onCategorySelected(it)
        }
    }

    private fun setupChooseCategory() {
        viewBinding.finish.setOnClickListener {
            dismiss()
        }
    }

    private fun onCategorySelected(category: Int) {
    }

    companion object {
        const val TAG = "br.touchetime.ui.categoryfragment"

        private fun newInstance() = CategoryFragment()

        fun show(fragmentManager: FragmentManager) = newInstance().show(fragmentManager, TAG)
    }
}
