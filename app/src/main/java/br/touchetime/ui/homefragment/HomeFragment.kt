package br.touchetime.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import br.touchetime.MainActivity
import br.touchetime.R
import br.touchetime.R.drawable
import br.touchetime.databinding.FragmentHomeBinding
import br.touchetime.ui.createfight.CreateFightFragment
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

        setupScoreFragmentDefault()
        setupScoreFragmentCustom()
        setupChooseFightAndCustom()
    }

    private fun setupScoreFragmentCustom() {
        viewBinding.customFight.setOnClickListener {
            mainActivity?.typeFight = CUSTOM_FIGHT
            navigateToFragment(ScoreFragment.newInstance(), ScoreFragment.TAG)
        }
    }

    private fun setupChooseFightAndCustom() {
        viewBinding.fight.apply {
            setTitle(context.getString(R.string.official_fight))
            setDescription(context.getString(R.string.choose_fight_description))
            setIcon(getDrawable(context, drawable.ic_users_fight))
            setIconVisibility(true)
        }

        viewBinding.customFight.apply {
            setTitle(context.getString(R.string.custom_fight))
            setDescription(context.getString(R.string.custom_fight_description))
            setIcon(getDrawable(context, drawable.ic_edit_fight))
            setIconVisibility(true)
        }
    }

    private fun setupScoreFragmentDefault() {
        viewBinding.fight.setOnClickListener {
            mainActivity?.typeFight = DEFAULT_FIGHT
            navigateToFragment(CreateFightFragment.newInstance(), CreateFightFragment.TAG)
        }
    }

    private fun navigateToFragment(fragment: Fragment, key: String) {
        mainActivity?.navigateToFragment(
            fragment,
            key
        )
    }

    companion object {
        const val TAG = "br.wrestling.ui.homefragment"
        const val DEFAULT_FIGHT = "DEFAULT_FIGHT"
        const val CUSTOM_FIGHT = "CUSTOM_FIGHT"

        fun newInstance() = HomeFragment()
    }
}
