package com.kpl.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kpl.R
import com.kpl.activity.InformationActivity
import com.kpl.activity.NotificationActivity
import com.kpl.extention.invisible
import com.kpl.interfaces.goToActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*


class SettingFragment : BaseFragment() {

    lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        intent = Intent(requireContext(), InformationActivity::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBack.invisible()
        txtTitle.text = "Setting"
        relayNotification.setOnClickListener {
            goToActivity<NotificationActivity>()
        }

        relayPrivacy.setOnClickListener {
            intent.putExtra("Title", "Privacy Policy")
            intent.putExtra("Desc", "")
            startActivity(intent)
        }

        relayAboutus.setOnClickListener {
            intent.putExtra("Title", "About Us")
            intent.putExtra("Desc", "")
            startActivity(intent)
        }
        relayTerms.setOnClickListener {
            intent.putExtra("Title", "Terms And Condition")
            intent.putExtra("Desc", "")
            startActivity(intent)
        }
    }
}