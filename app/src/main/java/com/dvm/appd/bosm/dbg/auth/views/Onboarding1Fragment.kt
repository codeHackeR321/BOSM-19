package com.dvm.appd.bosm.dbg.auth.views


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.fragment_onboarding1.*

interface onboardingFragmentSkipButtonClickListener {
    fun onSkipButtonPressed()
}

class Onboarding1Fragment(val listener: onboardingFragmentSkipButtonClickListener) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        text_bttn_skip.setOnClickListener {
            listener.onSkipButtonPressed()
        }
        super.onViewCreated(view, savedInstanceState)
    }

}
