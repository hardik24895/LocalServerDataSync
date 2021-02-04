package com.sprinters.ui.dialog

import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import com.kpl.utils.BlurBottomSheetDialog
import com.ms_square.etsyblur.BlurConfig


open class BaseBottomSheetDialogFragment : BlurBottomSheetDialog() {

    override fun show(manager: FragmentManager, tag: String?) {
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.add(this, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun dismissDialog() {
        dismissAllowingStateLoss()
    }

    @NonNull
    override fun blurConfig(): BlurConfig {
        return BlurConfig.Builder()
            .asyncPolicy(BlurConfig.DEFAULT_ASYNC_POLICY)
            .debug(true)
            .build()
    }
}