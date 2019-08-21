package com.dvm.appd.bosm.dbg.profile.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.views.AuthActivity
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModel
import com.dvm.appd.bosm.dbg.profile.viewmodel.ProfileViewModelFactory
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dia_wallet_add_money.view.*
import kotlinx.android.synthetic.main.fra_profile.view.*

class ProfileFragment : Fragment() {

    private val profileViewModel by lazy {
        ViewModelProviders.of(this, ProfileViewModelFactory())[ProfileViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fra_profile, container, false)
        activity!!.my_toolbar.visibility = View.GONE

        rootView.logout.setOnClickListener {
             profileViewModel.logout()
        }
   rootView.AddBtn.setOnClickListener {
       it.findNavController().navigate(R.id.action_action_profile_to_addMoneyDialog)
   }
        rootView.backBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
        profileViewModel.order.observe(this, Observer {
            when(it!!){
                UiState.MoveToLogin -> {
                    activity!!.finishAffinity()
                    startActivity(Intent(context!!,AuthActivity::class.java))
                }
                UiState.ShowIdle ->{
                    rootView.loading.visibility = View.GONE
                }
                UiState.ShowLoading -> {
                    rootView.loading.visibility = View.VISIBLE
                }
            }
        })

        profileViewModel.user.observe(this, Observer {
            rootView.username.text = it.name
            rootView.userId.text = "User id: ${it.userId}"
            Observable.just(it.qrCode.generateQr())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    rootView.qrCode.setImageBitmap(it)
                }
        })
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        activity!!.my_toolbar.visibility = View.VISIBLE
    }

    fun String.generateQr(): Bitmap {
        val bitMatrix = MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE,400,400)
        return BarcodeEncoder().createBitmap(bitMatrix)
    }
}