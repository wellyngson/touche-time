package br.touchetime.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.touchetime.MainActivity
import br.touchetime.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        goToMainScreen()
    }

    private fun goToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(baseContext, MainActivity::class.java).apply {
                    putExtras(intent)
                    data = intent.data
                }
            )
        }, OPEN_DELAY)
    }

    companion object {
        private const val OPEN_DELAY = 3000L
    }
}
