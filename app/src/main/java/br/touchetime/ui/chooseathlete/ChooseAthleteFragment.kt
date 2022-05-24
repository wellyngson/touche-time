package br.touchetime.ui.chooseathlete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.data.model.Athlete
import br.touchetime.databinding.FragmentChooseAthleteBinding
import br.touchetime.ui.createathlete.CreateAthleteFragment
import br.touchetime.ui.scorefragment.ScoreFragment
import br.touchetime.ui.selectathlete.SelectAthleteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseAthleteFragment : Fragment() {

    private lateinit var viewBinding: FragmentChooseAthleteBinding
    private val viewModel: ChooseAthleteViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity
    private val resultKeys = arrayOf(
        SelectAthleteFragment.RED,
        SelectAthleteFragment.BLUE
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentChooseAthleteBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAthleteSelected(RED)
        setupResultKeyListeners()
        setupRedBlueAthlete()
        setupRedAthete()
        setupRedObserver()
        setupBlueAthlete()
        setupBlueObserver()
        setupStartFight()
        setupCreateAthlete()
    }

    private fun setupCreateAthlete() {
        viewBinding.createAthlete.setOnClickListener {
            mainActivity?.navigateToFragment(
                CreateAthleteFragment.newInstance(),
                CreateAthleteFragment.TAG
            )
        }
    }

    private fun setupAthleteSelected(const: String) {
        viewModel.setupAthleteSelected(const)
    }

    private fun setupStartFight() {
        viewBinding.startFight.setOnClickListener {
            mainActivity?.navigateToFragment(
                ScoreFragment.newInstance(
                    viewModel.athleteRed.value,
                    viewModel.athleteBlue.value
                ),
                ScoreFragment.TAG,
            )
        }
    }

    private fun setupResultKeyListeners() {
        resultKeys.forEach {
            when (it) {
                SelectAthleteFragment.RED,
                SelectAthleteFragment.BLUE,
                -> childFragmentManager
                else -> activity?.supportFragmentManager
            }?.setFragmentResultListener(
                it,
                viewLifecycleOwner,
                ::handleResultKey
            )
        }
    }

    private fun handleResultKey(key: String, bundle: Bundle) {
        when (key) {
            SelectAthleteFragment.RED -> setRedAthlete(bundle)
            SelectAthleteFragment.BLUE -> setBlueAthlete(bundle)
        }
    }

    private fun setRedAthlete(bundle: Bundle) {
        bundle.getParcelable<Athlete>(SelectAthleteFragment.ATHLETE)?.let {
            viewModel.setupRedAthlete(it)
        }
    }

    private fun setBlueAthlete(bundle: Bundle) {
        bundle.getParcelable<Athlete>(SelectAthleteFragment.ATHLETE)?.let {
            viewModel.setupBlueAthlete(it)
        }
    }

    private fun setupRedBlueAthlete() {
        viewBinding.apply {
            redAthlete.apply {
                setTitle(context.getString(R.string.select_athlete_title))
                setDescription(context.getString(R.string.select_athlete_description))
            }

            blueAthlete.apply {
                setTitle(context.getString(R.string.select_athlete_title))
                setDescription(context.getString(R.string.select_athlete_description))
            }
        }
    }

    private fun setupRedAthete() {
        viewBinding.redAthlete.setOnClickListener {
            setupAthleteSelected(RED)
            openSelectAthlete(SelectAthleteFragment.RED)
        }
    }

    private fun setupRedObserver() {
        viewModel.athleteRed.observe(viewLifecycleOwner) {
            viewBinding.apply {
                redAthleteSelected.isVisible = it != null
                redAthleteSelected.text = it.name
            }
        }
    }

    private fun setupBlueAthlete() {
        viewBinding.blueAthlete.setOnClickListener {
            setupAthleteSelected(BLUE)
            openSelectAthlete(SelectAthleteFragment.BLUE)
        }
    }

    private fun setupBlueObserver() {
        viewModel.athleteBlue.observe(viewLifecycleOwner) {
            viewBinding.apply {
                blueAthleteSelected.isVisible = it != null
                blueAthleteSelected.text = it.name
            }
        }
    }

    private fun openSelectAthlete(athleteSelected: String) {
        SelectAthleteFragment.show(childFragmentManager, athleteSelected)
    }

    companion object {
        const val TAG = "br.touchetime.ui.chooseathlete"
        const val RED = "red"
        const val BLUE = "blue"

        fun newInstance() = ChooseAthleteFragment()
    }
}
