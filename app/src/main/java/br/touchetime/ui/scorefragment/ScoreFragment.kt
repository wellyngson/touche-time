package br.touchetime.ui.scorefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.touchetime.MainActivity
import br.touchetime.databinding.FragmentScoreBinding
import br.touchetime.ui.homefragment.HomeFragment
import kotlinx.coroutines.flow.collect

class ScoreFragment : Fragment() {

    private lateinit var viewBinding: FragmentScoreBinding
    private val viewModel: ScoreViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentScoreBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupListenersRed()
        setupListenersBlue()
        setupScoreFragment()
        setupObservers()
    }

    private fun setupListenersRed() {
        viewBinding.red.viewBindingComponent.addScore.setOnClickListener {
            viewModel.addScoreRed()
        }

        viewBinding.red.viewBindingComponent.removeScore.setOnClickListener {
            viewModel.removeScoreRed()
        }
    }

    private fun setupListenersBlue() {
        viewBinding.blue.viewBindingComponent.addScore.setOnClickListener {
            viewModel.addScoreBlue()
        }

        viewBinding.blue.viewBindingComponent.removeScore.setOnClickListener {
            viewModel.removeScoreBlue()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.scoreRed.collect { scoreRedState ->
                when (scoreRedState) {
                    is ScoreViewModel.UiState.Success -> {
                        viewBinding.red.viewBindingComponent.score.text =
                            scoreRedState.score.toString()
                    }
                    is ScoreViewModel.UiState.Error -> {
                        Toast.makeText(
                            context,
                            "O atleta não tem ponto para ser retirado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ScoreViewModel.UiState.Finish -> {
                        viewBinding.red.viewBindingComponent.score.text =
                            scoreRedState.scoreFinal.toString()

                        showWinner(RED)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.scoreBlue.collect { scoreBlueState ->
                when (scoreBlueState) {
                    is ScoreViewModel.UiState.Success -> {
                        viewBinding.blue.viewBindingComponent.score.text =
                            scoreBlueState.score.toString()
                    }
                    is ScoreViewModel.UiState.Error -> {
                        Toast.makeText(
                            context,
                            "O atleta não tem ponto para ser retirado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ScoreViewModel.UiState.Finish -> {
                        viewBinding.blue.viewBindingComponent.score.text =
                            scoreBlueState.scoreFinal.toString()

                        showWinner(BLUE)
                    }
                }
            }
        }
    }

    private fun setupScoreFragment() {
        viewBinding.back.setOnClickListener {
            mainActivity?.navigateToFragment(
                HomeFragment.newInstance(),
                HomeFragment.TAG
            )
        }
    }

    private fun showWinner(athlete: String) {
        Toast.makeText(
            context,
            "Atleta $athlete venceu",
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val TAG = "br.wrestling.ui.scorefragment"
        const val RED = "red"
        const val BLUE = "blue"
        const val DISMISS_RESULT = "DISMISS_RESULT"

        fun newInstance() = ScoreFragment()
    }
}
