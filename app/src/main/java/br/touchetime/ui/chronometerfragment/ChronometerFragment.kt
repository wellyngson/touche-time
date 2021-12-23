package br.touchetime.ui.chronometerfragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.touchetime.R
import br.touchetime.databinding.FragmentChronometerBinding
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
        setupListenersRefreshAndEdit()
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
                Toast.makeText(context, "Edit clicado", Toast.LENGTH_SHORT).show()
            }
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
            }

            override fun onTick(millisUntilFinished: Long) {
                this@ChronometerFragment.timerRound = millisUntilFinished
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
                this@ChronometerFragment.timerRound = START_ROUND
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
