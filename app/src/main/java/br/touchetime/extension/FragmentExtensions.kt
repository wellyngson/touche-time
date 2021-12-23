package br.touchetime.extension

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope

fun Fragment.doWhenResumed(block: Fragment.() -> Unit) {
    lifecycleScope.launchWhenResumed {
        block()
    }
}

fun Fragment.setFragmentResultsListeners(
    manager: FragmentManager,
    keys: Array<String>,
    handler: (key: String, bundle: Bundle) -> Unit,
) {
    keys.forEach {
        manager.setFragmentResultListener(it, viewLifecycleOwner, handler)
    }
}

fun Fragment.isParentAheadFragment(): Boolean = try {
    parentFragmentManager.fragments.lastOrNull() == this
} catch (e: Exception) {
    false
}

fun Fragment.tryGetChildFragmentManager(): FragmentManager? = try {
    childFragmentManager
} catch (e: Exception) {
    null
}
fun Fragment.tryGetParentFragmentManager(): FragmentManager? = try {
    parentFragmentManager
} catch (e: Exception) {
    null
}


fun Fragment.getMaxElevation() = tryGetParentFragmentManager()
    ?.fragments
    ?.maxOfOrNull { it.view?.elevation ?: 0f } ?: 0f

val Fragment.appCompatActivity: AppCompatActivity?
    get() = activity as? AppCompatActivity
