package com.dvm.appd.bosm.dbg.auth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.di.AppModule
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.ogaclejapan.smarttablayout.SmartTabLayout
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity(), onboardingFragmentSkipButtonClickListener {

    lateinit var pageAdapter: FragmentStatePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        pageAdapter = object: FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return Onboarding1Fragment(this@OnboardingActivity)
            }

            override fun getCount(): Int {
                return 3
            }
        }
        viewPager_onBoarding.adapter = pageAdapter
        bttn_next_onBoarding.setOnClickListener {
            if (viewPager_onBoarding.currentItem == 2) {
                finishOnBoarding()
            } else {
                viewPager_onBoarding.setCurrentItem(viewPager_onBoarding.currentItem + 1, true)
            }
        }
    }

    override fun onSkipButtonPressed() {
        finishOnBoarding()
    }

    private fun finishOnBoarding() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
