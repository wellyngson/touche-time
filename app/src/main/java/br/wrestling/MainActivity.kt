package br.wrestling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import br.wrestling.databinding.ActivityMainBinding
import br.wrestling.ui.homefragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupHomeFragment()
    }

    private fun setupHomeFragment() {
        supportFragmentManager.commit(true) {
            replace(viewBinding.container.id, HomeFragment.newInstance(), HomeFragment.TAG)
        }
    }

    fun navigateToFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager.commit(true) {
            replace(viewBinding.container.id, fragment, tag)
        }
    }
}
