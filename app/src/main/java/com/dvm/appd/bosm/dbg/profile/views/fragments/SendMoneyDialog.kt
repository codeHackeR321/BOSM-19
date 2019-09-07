package com.dvm.appd.bosm.dbg.profile.views.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.profile.viewmodel.SendMoneyViewModel
import com.dvm.appd.bosm.dbg.profile.viewmodel.SendMoneyViewModelFactory
import kotlinx.android.synthetic.main.dia_wallet_send_money.view.*


class SendMoneyDialog : DialogFragment() {

    private lateinit var sendMoneyViewModel: SendMoneyViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.dia_wallet_send_money, container, false)
        sendMoneyViewModel =
            ViewModelProviders.of(this, SendMoneyViewModelFactory())[SendMoneyViewModel::class.java]
        rootView.SendBtn.isClickable = true
        rootView.SendBtn.setOnClickListener {
            if (rootView.Amount.text.toString().isBlank() || rootView.userId.text.toString().isBlank()) {
                Toast.makeText(context!!, "Please fill all the required fields!", Toast.LENGTH_SHORT).show()
            } else {
                rootView.SendBtn.isClickable =false
                sendMoneyViewModel.transferMoney(
                    rootView.userId.text.toString().toInt(),
                    rootView.Amount.text.toString().toInt()
                )

            }
        }
        sendMoneyViewModel.result.observe(this, Observer {
            when(it!!){
                TransactionResult.Success -> {
                    dismiss()
                    Toast.makeText(context!!, "Money transferred successfully!", Toast.LENGTH_SHORT).show()
                }
                is TransactionResult.Failure -> {
                    Toast.makeText(context!!, (it as TransactionResult.Failure).message, Toast.LENGTH_SHORT).show()
                    rootView.SendBtn.isClickable = true
                }
            }
        })
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}