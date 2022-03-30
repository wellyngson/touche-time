package br.touchetime.ui.createathlete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.touchetime.databinding.FragmentCreateAthleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAthleteFragment : Fragment() {

    private lateinit var viewBinding: FragmentCreateAthleteBinding
    private val viewModel: CreateAthleteViewModel by viewModels()

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

        setupCreateAthlete()
    }

    private fun setupCreateAthlete() {
        viewBinding.createAthlete.setOnClickListener {
            viewModel.createAthlete()
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.createathlete"

        fun newInstance() = CreateAthleteFragment()
    }
}
