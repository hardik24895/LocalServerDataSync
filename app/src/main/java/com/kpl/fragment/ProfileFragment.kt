package com.kpl.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.whenStarted
import com.kpl.R
import com.kpl.activity.LoginActivity
import com.kpl.database.Category
import com.kpl.database.Employee
import com.kpl.database.Project
import com.kpl.database.Question
import com.kpl.extention.*
import com.kpl.interfaces.goToActivity
import com.kpl.model.*
import com.kpl.network.CallbackObserver
import com.kpl.network.Networking
import com.kpl.network.addTo
import com.kpl.utils.SessionManager
import com.kpl.utils.SessionManager.Companion.SPEmailID
import com.kpl.utils.SessionManager.Companion.SPFirstName
import com.kpl.utils.SessionManager.Companion.SPLastName
import com.kpl.utils.SessionManager.Companion.SPUserID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar_with_back_arrow.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgBack.invisible()
        txtTitle.text = "PROFILE"
        edtFirstName.setText(session.getDataByKey(SessionManager.SPFirstName))
        edtLastName.setText(session.getDataByKey(SessionManager.SPLastName))
        edtContact.setText(session.getDataByKey(SessionManager.SPMobileNo))
        edtEmail.setText(session.getDataByKey(SessionManager.SPEmailID))
        txtUpdate.setOnClickListener {
            isValid()

        }

    }

    private fun isValid() {
        when {
            !isValidName(edtFirstName.getValue()) -> {
                root.showSnackBar("Enter First Name")
                edtFirstName.requestFocus()
            }

            !isValidName(edtLastName.getValue()) -> {
                root.showSnackBar("Enter Last Name")
                edtLastName.requestFocus()
            }
            !isValidEmail(edtEmail.getValue()) -> {
                root.showSnackBar("Enter Valid Email")
                edtEmail.requestFocus()
            }
            else -> updateProfile()


        }

    }


    fun updateProfile() {
        var result = ""
        showProgressbar()
        try {
            val jsonObject = JSONObject()
            jsonObject.put("FirstName", edtFirstName.text.toString())
            jsonObject.put("LastName", edtLastName.text.toString())
            jsonObject.put("EmailID", edtEmail.text.toString())
            jsonObject.put("UserID", session.getDataByKey(SPUserID))

            // jsonBody.put("body", jsonObject)

            result = Networking.setParentJsonData("editProfile", jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Networking
            .with(requireContext())
            .getServices()
            .UpdateUserProfile(Networking.wrapParams(result))//wrapParams Wraps parameters in to Request body Json format
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserver<UpdateProfileDataModel>() {
                override fun onSuccess(response: UpdateProfileDataModel) {
                    hideProgressbar()
                    if (response.error == 200) {

                        val mainLooper = Looper.getMainLooper()
                        Thread(Runnable {
                            Handler(mainLooper).post {
                                session.storeDataByKey(SPFirstName, edtFirstName.text.toString())
                                session.storeDataByKey(SPLastName, edtLastName.text.toString())
                                session.storeDataByKey(SPEmailID, edtEmail.text.toString())

                            }
                        }).start()
                        root.showSnackBar(response.message.toString())
                        showAlert(response.message.toString())
                    } else {
                        showAlert(response.message.toString())
                    }
                }

                override fun onFailed(code: Int, message: String) {
                    showAlert(message)
                    hideProgressbar()
                }

            }).addTo(autoDisposable)
    }
}