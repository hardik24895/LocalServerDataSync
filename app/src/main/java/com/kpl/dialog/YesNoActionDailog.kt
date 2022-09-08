package com.kpl.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.kpl.R
import com.kpl.extention.invisible
import com.kpl.extention.visible
import com.kpl.network.AutoDisposable
import com.kpl.utils.BlurDialogFragment
import com.kpl.utils.Constant
import com.kpl.utils.SessionManager
import kotlinx.android.synthetic.main.dialog_yes_no_action.*


class YesNoActionDailog(context: Context) : BlurDialogFragment(), LifecycleOwner {
    private val autoDisposable = AutoDisposable()
    private lateinit var session: SessionManager


    companion object {
        private lateinit var listener: onItemClick
        fun newInstance(
            context: Context,
            listeners: onItemClick
        ): YesNoActionDailog {
            this.listener = listeners
            return YesNoActionDailog(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        session = SessionManager(requireContext())
        autoDisposable.bindTo(this.lifecycle)
        return inflater.inflate(R.layout.dialog_yes_no_action, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateData()
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        linOK.invisible()
        linYseNo.visible()
        tvOk.setOnClickListener {
            listener.onItemCLicked()
            dismissAllowingStateLoss()
        }
        tvYes.setOnClickListener {
            listener.onItemCLicked()
            dismissAllowingStateLoss()
        }
        tvNo.setOnClickListener {
            dismissAllowingStateLoss()
        }

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    private fun populateData() {
        val bundle = arguments
        if (bundle != null) {
            val title = bundle.getString(Constant.TITLE)
            val text = bundle.getString(Constant.TEXT)
            txtTitle.text = title
            tvText.text = text
        }
    }

    interface onItemClick {
        fun onItemCLicked()
    }

    interface onDissmiss {
        fun onDismiss()
    }
}








