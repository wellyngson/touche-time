package br.touchetime.ui.scorefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import br.touchetime.MainActivity
import br.touchetime.databinding.FragmentScoreBinding
import br.touchetime.model.UiStateScore
import br.touchetime.ui.chronometerfragment.ChronometerFragment
import br.touchetime.ui.homefragment.HomeFragment
import kotlinx.coroutines.flow.collect

class ScoreFragment : Fragment() {

    private lateinit var viewBinding: FragmentScoreBinding
    private val viewModel: ScoreViewModel by activityViewModels()
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
        setupChronometer()
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
                    is UiStateScore.Success -> {
                        viewBinding.red.setScore(scoreRedState.score.toString())
                    }
                    is UiStateScore.Error -> {
                        Toast.makeText(
                            context,
                            "O atleta não tem ponto para ser retirado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is UiStateScore.Finish -> {
                        viewBinding.red.setScore(scoreRedState.scoreFinal.toString())

                        showWinner(RED)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.scoreBlue.collect { scoreBlueState ->
                when (scoreBlueState) {
                    is UiStateScore.Success -> {
                        viewBinding.blue.setScore(scoreBlueState.score.toString())
                    }
                    is UiStateScore.Error -> {
                        Toast.makeText(
                            context,
                            "O atleta não tem ponto para ser retirado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is UiStateScore.Finish -> {
                        viewBinding.blue.setScore(scoreBlueState.scoreFinal.toString())

                        showWinner(BLUE)
                    }
                }
            }
        }
    }

    private fun setupChronometer() {
        childFragmentManager.commit(true) {
            replace(
                viewBinding.chronometer.id,
                ChronometerFragment.newInstance(),
                ChronometerFragment.TAG
            )
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

        fun newInstance() = ScoreFragment()
    }
}
