package br.wrestling.ui.chronometerfragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.wrestling.R
import br.wrestling.databinding.FragmentChronometerBinding
import kotlinx.android.synthetic.main.fragment_chronometer.view.*

class ChronometerFragment : Fragment() {

    private lateinit var viewBinding: FragmentChronometerBinding
    private lateinit var chronometerRound: CountDownTimer
    private lateinit var chronometerInterval: CountDownTimer

    private var chronometerRoundIsFinished: Boolean = false
    private var timerRound = START_ROUND
    private var timerInterval = START_INTERVAL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentChronometerBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupChronometer()
    }

    private fun setupChronometer() {
        viewBinding.playOrPause.apply {
            isSelected = false

            setOnClickListener {
                setupStateButtonPlayPause()

                if (chronometerRoundIsFinished) {
//                    changeVisibilitySettingsChronometer(false)
                    setupStartChronometerInterval(timerInterval)
                } else {
                    if (isSelected) {
//                        changeVisibilitySettingsChronometer(false)
                        setupStartChronometer(timerRound)
                    } else {
//                        changeVisibilitySettingsChronometer(false)
                        pauseTimer()
                    }
                }
            }
        }
    }

    private fun changeVisibilitySettingsChronometer(condition: Boolean) {
        if (condition) {
            viewBinding.refresh.visibility = View.VISIBLE
            viewBinding.edit.visibility = View.VISIBLE
        } else {
            viewBinding.refresh.visibility = View.GONE
            viewBinding.edit.visibility = View.GONE
        }
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
                this@ChronometerFragment.timerInterval = START_INTERVAL
//                changeVisibilitySettingsChronometer(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                this@ChronometerFragment.timerRound = millisUntilFinished
                setTextTimerFormat(millisUntilFinished)
            }
        }.start()
    }

    private fun setupStateButtonPlayPause() {
        viewBinding.apply {
            playOrPause.isSelected = !playOrPause.isSelected
            changeVisibilitySettingsChronometer(!playOrPause.isSelected)
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
                this@ChronometerFragment.timerRound = START_ROUND
//                changeVisibilitySettingsChronometer(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                this@ChronometerFragment.timerInterval = millisUntilFinished
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
        const val TAG = "br.wrestling.ui.chronometerfragment"
        const val START_ROUND = 10000L // 180000L
        const val START_INTERVAL = 5000L // 30000L

        fun newInstance() = ChronometerFragment()
    }
}
