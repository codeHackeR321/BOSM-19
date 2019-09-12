package com.dvm.appd.bosm.dbg.events.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.auth.data.repo.AuthRepository.Keys.name
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData
import com.dvm.appd.bosm.dbg.events.view.adapters.GenderDataAdapter
import com.dvm.appd.bosm.dbg.events.view.adapters.SportsDataAdapter
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModel
import com.dvm.appd.bosm.dbg.events.viewmodel.SportsDataViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sports_data.*
import kotlinx.android.synthetic.main.fragment_sports_data.view.*
import kotlinx.android.synthetic.main.fragment_sports_data.view.recy_sports_vertical
import java.lang.Exception

class SportsDataFragment : Fragment(),GenderDataAdapter.OnGenderClicked, SportsDataAdapter.OnFavouriteClicked {
    private var genderSelected=""
    private var genderWiseDataMap= mapOf<String,List<SportsData>>()
    private lateinit var sportsDataViewModel: SportsDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var sportName= arguments?.getString("name")
        Log.d("Sports", "sports name selected: $name")
        sportsDataViewModel = ViewModelProviders.of(this, SportsDataViewModelFactory(sportName!!))[SportsDataViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_sports_data, container, false)
        (activity!! as MainActivity).hideCustomToolbarForLevel2Fragments()
        activity!!.search.isVisible = false
        activity!!.textView7.isVisible = false
        activity!!.refresh.isVisible = false


        view.textView4.text=sportName.capitalize()

        view.recy_sports_horizontal.adapter = GenderDataAdapter(genderSelected,this)
        view.recy_sports_vertical.adapter = SportsDataAdapter(this)

        sportsDataViewModel.gender.observe(this, Observer {
            Log.d("Sports11" , "Entered observer for gender Adapter with list = $it")
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).gender = it
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).notifyDataSetChanged()

            if (genderSelected.isEmpty()&&it.isNotEmpty())
            {
                genderSelected=it[0]
                setGenderWiseData()
            }
           // removeLoadingStateActivity()
            Log.d("SportsFRag", "Observed")

            /*(view.recy_sports_horizontal.adapter as GenderDataAdapter).genderSelected = genderSelected
            (view.recy_sports_horizontal.adapter as GenderDataAdapter).notifyDataSetChanged()

            (view.recy_sports_vertical.adapter as SportsDataAdapter).genderSelected=it[0]
            (view.recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
*/
        })

        /*sportsDataViewModel.sportsData.observe(this, Observer {

            Log.d("SportsFRag", "Observed")
            (view.recy_sports_vertical.adapter as SportsDataAdapter).sportData = it[genderSelected] ?: error("Data for $genderSelected not found")
            (view.recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
        })
        */

        sportsDataViewModel.error.observe(this, Observer {
            if (it != null){
                Toast.makeText(context!!, it, Toast.LENGTH_SHORT).show()
                (sportsDataViewModel.error as MutableLiveData).postValue(null)
            }
        })
        setGenderWiseDataObserver()

        view.back.setOnClickListener {
           it.findNavController().popBackStack()
        }

        return view
    }

    override fun genderClicked(gender: String) {
        genderSelected=gender
        Toast.makeText(activity, "Gender selected$gender", Toast.LENGTH_LONG).show()
        setGenderWiseData()
        /*
        (recy_sports_vertical.adapter as SportsDataAdapter).genderSelected=gender
        (recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
   */ }

    private fun setGenderWiseData(){
        try {

            //date wise data supplied
            // null agar hua tio dikkat nhi hobni chhiye shayd
            if (genderWiseDataMap[genderSelected].isNullOrEmpty())
            {
                Toast.makeText(activity, "No data for $genderSelected", Toast.LENGTH_LONG).show()
                (recy_sports_vertical.adapter as SportsDataAdapter).sportData= emptyList()
            }
            else
                (recy_sports_vertical.adapter as SportsDataAdapter).sportData =genderWiseDataMap[genderSelected] ?: error("No data Found $genderSelected")

            //Check daywise data
            Log.d("CompletedDataCheck", "data Check${genderWiseDataMap[genderSelected]}.dayWiseorders.toString()}")
            (recy_sports_vertical.adapter as SportsDataAdapter).notifyDataSetChanged()
            //to change selected value of date
            (recy_sports_horizontal.adapter as GenderDataAdapter).genderSelected=genderSelected
            (recy_sports_horizontal.adapter as GenderDataAdapter).notifyDataSetChanged()

        } catch (e: Exception) {
            Log.d("CompletedError", e.toString())
            Toast.makeText(activity,"My message$e" , Toast.LENGTH_LONG).show()
        }
    }

    private fun setGenderWiseDataObserver() {
        sportsDataViewModel.sportsData.observe(this, Observer {
            Log.d("SportsFragment", "Observer Called With data = ${it.toString()}")
            genderWiseDataMap = it
            setGenderWiseData()
        })
    }

    override fun updateFavourite(matchNo: Int, favouriteMark: Int) {
        sportsDataViewModel.markMatchFavourite(matchNo, favouriteMark)
    }
}
