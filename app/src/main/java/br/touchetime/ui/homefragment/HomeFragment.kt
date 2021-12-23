package br.touchetime.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.R.drawable
import br.touchetime.databinding.FragmentHomeBinding
import br.touchetime.ui.scorefragment.ScoreFragment

class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupScoreFragment()
        setupChooseFightAndCustom()
    }

    private fun setupChooseFightAndCustom() {
        viewBinding.customFight.apply {
            setTitle(context.getString(R.string.custom_fight))
            setDescription(context.getString(R.string.custom_fight_description))
            setIcon(context.getDrawable(drawable.ic_edit_fight))
        }

        viewBinding.fight.apply {
            setTitle(context.getString(R.string.official_fight))
            setDescription(context.getString(R.string.choose_fight_description))
            setIcon(context.getDrawable(drawable.ic_users_fight))
        }
    }

    private fun setupScoreFragment() {
        viewBinding.fight.setOnClickListener {
            mainActivity?.navigateToFragment(
                ScoreFragment.newInstance(),
                ScoreFragment.TAG
            )
        }
    }

    companion object {
        const val TAG = "br.wrestling.ui.homefragment"

        fun newInstance() = HomeFragment()
    }
}
