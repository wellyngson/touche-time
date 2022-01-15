package br.touchetime.ui.scorefragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.data.model.UiState
import br.touchetime.databinding.FragmentScoreBinding
import br.touchetime.ui.editfightfragment.EditFightBottomSheetFragment
import br.touchetime.ui.homefragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ScoreFragment : Fragment() {

    private lateinit var viewBinding: FragmentScoreBinding
    private val viewModel: ScoreViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    private lateinit var chronometerRound: CountDownTimer
    private lateinit var chronometerInterval: CountDownTimer

    private var chronometerRoundIsFinished: Boolean = false
    private var timerRound = START_ROUND
    private var timerInterval = START_INTERVAL

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

        setupTypeFight()
        setupListenersRed()
        setupListenersBlue()
        setupChronometer()
        setupListenersRefreshAndEdit()
        setupObservers()
        setupHomeFragment()
    }

    private fun setupTypeFight() {
        if (mainActivity?.typeFight == HomeFragment.DEFAULT_FIGHT) {
            View.GONE.let {
                viewBinding.apply {
                    refresh.visibility = it
                    edit.visibility = it
                }
            }
        }
    }

    private fun setupListenersRed() {
        viewBinding.red.apply {
            addScore.setOnClickListener {
                viewModel.addScoreRed()
            }

            removeScore.setOnClickListener {
                viewModel.removeScoreRed()
            }
        }
    }

    private fun setupListenersBlue() {
        viewBinding.blue.apply {
            addScore.setOnClickListener {
                viewModel.addScoreBlue()
            }

            removeScore.setOnClickListener {
                viewModel.removeScoreBlue()
            }
        }
    }

    private fun setupChronometer() {
        viewBinding.playOrPause.apply {
            isSelected = false

            setOnClickListener {
                setupStateButtonPlayPause()

                if (chronometerRoundIsFinished) {
                    setupStartChronometerInterval(timerInterval)
                } else {
                    if (isSelected) {
                        setupStartChronometer(timerRound)
                    } else {
                        pauseTimer()
                    }
                }
            }
        }
    }

    private fun setupListenersRefreshAndEdit() {
        viewBinding.apply {
            refresh.setOnClickListener {
                Toast.makeText(context, "Refresh clicado", Toast.LENGTH_SHORT).show()
            }

            edit.setOnClickListener {
                EditFightBottomSheetFragment.show(
                    childFragmentManager
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.scoreRed.collect { scoreRed ->
                when (scoreRed.state) {
                    UiState.Initial, UiState.Success -> {
                        viewBinding.red.score.text = scoreRed.score.toString()
                    }
                    UiState.Error -> {
                        Toast.makeText(
                            context,
                            "O atleta não tem ponto para ser retirado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    UiState.Finish -> {
                        viewBinding.red.score.text = scoreRed.score.toString()

                        showWinner(RED)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.scoreBlue.collect { scoreBlueState ->
                when (scoreBlueState.state) {
                    UiState.Initial, UiState.Success -> {
                        viewBinding.blue.score.text = scoreBlueState.score.toString()
                    }
                    UiState.Error -> {
                        Toast.makeText(
                            context,
                            "O atleta não tem ponto para ser retirado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    UiState.Finish -> {
                        viewBinding.blue.score.text = scoreBlueState.score.toString()

                        showWinner(BLUE)
                    }
                }
            }
        }
    }

    private fun setupHomeFragment() {
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

    private fun setupStartChronometer(timerRound: Long) {
        chronometerRound = object : CountDownTimer(timerRound, 1000) {

            override fun onFinish() {
                setupStateButtonPlayPause()
                chronometerRoundIsFinished = true
                viewBinding.apply {
                    tenSecondsChronometer.text = "3"
                    round.setText(R.string.interval)
                }
                this@ScoreFragment.timerInterval = START_INTERVAL
            }

            override fun onTick(millisUntilFinished: Long) {
                this@ScoreFragment.timerRound = millisUntilFinished
                setTextTimerFormat(millisUntilFinished)
            }
        }.start()
    }

    private fun setupStateButtonPlayPause() {
        viewBinding.apply {
            playOrPause.isSelected.let { boolean ->
                playOrPause.isSelected = !boolean
                edit.isEnabled = boolean
                refresh.isEnabled = boolean
            }
        }
    }

    private fun setupStartChronometerInterval(timerInterval: Long) {
        chronometerInterval = object : CountDownTimer(timerInterval, 1000) {

            override fun onFinish() {
                setupStateButtonPlayPause()
                chronometerRoundIsFinished = false
                viewBinding.apply {
                    minutesChronometer.text = "3"
                    round.setText(R.string.round_2)
                }
                this@ScoreFragment.timerRound = START_ROUND
            }

            override fun onTick(millisUntilFinished: Long) {
                this@ScoreFragment.timerInterval = millisUntilFinished
                setTextTimerFormat(millisUntilFinished)
            }
        }.start()
    }

    private fun pauseTimer() {
        chronometerRound.cancel()
    }

    private fun setTextTimerFormat(timer: Long) {
        val minutes = ((timer / 1000) / 60).toString()
        val seconds = ((timer / 1000) % 60)

        val formatSeconds = String.format("%02d", seconds)

        viewBinding.apply {
            minutesChronometer.text = minutes.substring(0)
            tenSecondsChronometer.text = formatSeconds.substring(0, 1)
            secondsChronometer.text = formatSeconds.substring(1)
        }
    }

    companion object {
        const val TAG = "br.wrestling.ui.scorefragment"
        const val RED = "red"
        const val BLUE = "blue"

        const val START_ROUND = 180000L // 10000L - 180000L
        const val START_INTERVAL = 30000L // 5000L - 30000L

        fun newInstance() = ScoreFragment()
    }
}
