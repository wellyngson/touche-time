package br.touchetime.ui.basedialogfragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import br.touchetime.R

open class DialogFragmentTransparentBackground : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.AlertDialogStyle)
        return super.onCreateDialog(savedInstanceState)
    }
}
