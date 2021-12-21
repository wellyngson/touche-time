package br.wrestling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import br.wrestling.databinding.ActivityMainBinding
import br.wrestling.ui.scorefragment.ScoreFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupScoreFragment()
    }

    private fun setupScoreFragment() {
        supportFragmentManager.commit(true) {
            replace(viewBinding.container.id, ScoreFragment.newInstance(), ScoreFragment.TAG)
        }
    }
}
