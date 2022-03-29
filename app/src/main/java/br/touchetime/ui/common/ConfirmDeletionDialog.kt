package br.touchetime.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import br.touchetime.databinding.ViewConfirmDeletionAlertDialogBinding
import br.touchetime.ui.basedialogfragment.DialogFragmentTransparentBackground

class ConfirmDeletionDialog : DialogFragmentTransparentBackground() {

    private lateinit var viewBinding: ViewConfirmDeletionAlertDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding =
            ViewConfirmDeletionAlertDialogBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        setupCancel()
        setupConfirm()
    }

    private fun setupCancel() {
        viewBinding.cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setupConfirm() {
        viewBinding.confirm.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                DELETE_ATHLETE, bundleOf()
            )

            dismiss()
        }
    }

    companion object {
        const val TAG = "br.touchetime.ui.common"
        const val DELETE_ATHLETE = "delete_athlete"

        fun newInstance() = ConfirmDeletionDialog()

        fun show(fragmentManager: FragmentManager) {
            newInstance().show(fragmentManager, TAG)
        }
    }
}
