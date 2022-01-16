package br.touchetime.ui.createfight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.databinding.FragmentCreateFightBinding
import br.touchetime.ui.categoryfragment.CategoryFragment
import br.touchetime.ui.homefragment.HomeFragment

class CreateFightFragment : Fragment() {

    private lateinit var viewBinding: FragmentCreateFightBinding
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

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
        setupBack()
    }

    private fun setupCategory() {
        viewBinding.category.apply {
            setTitle(getString(R.string.category_title))
            setDescription(getString(R.string.category_description))
            setIconVisibility(false)

            setOnClickListener {
                CategoryFragment.show(parentFragmentManager)
            }
        }
    }

    private fun setupStyle() {
        viewBinding.style.apply {
            setTitle(getString(R.string.style_title))
            setDescription(getString(R.string.style_description))
            setIconVisibility(false)

            setOnClickListener {
            }
        }
    }

    private fun setupWeight() {
        viewBinding.weight.apply {
            setTitle(getString(R.string.weight_title))
            setDescription(getString(R.string.weight_description))
            setIconVisibility(false)

            setOnClickListener {
            }
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

    companion object {
        const val TAG = "br.touchetime.ui.createfight"

        fun newInstance() = CreateFightFragment()
    }
}
