package com.dvm.appd.bosm.dbg.more

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.MainActivity

import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.more.dataClasses.Developer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fragment_recycler_view.*

class FragmentRecyclerView : Fragment() {

    lateinit var title: String
    private val picBaseUrl = "https://bits-apogee.org/backend-static/registrations/images/"
    private val developers = listOf(
        Developer("Prarabdh Garg", "App Developer", picBaseUrl + "prarabdh.png"),
        Developer("Suyash Soni", "App Developer", picBaseUrl + "suyash.png"),
        Developer("Akshat Gupta", "App Developer", picBaseUrl + "akshat.png"),
        Developer("Ishita Aggarwal", "App Developer", picBaseUrl + "ishita.png"),
        Developer("Pradyumna Bang", "Backend Developer", picBaseUrl + "raghav.png"),
        Developer("Dushyant Yadav", "Backend Developer", picBaseUrl + "raghav.png"),
        Developer("Shivanshu Ayachi", "Backend Developer", picBaseUrl + "raghav.png"),
        Developer("Abhinav Tiwari", "Backend Developer", picBaseUrl + "raghav.png"),
        Developer("Divyansh Jain", "Backend Developer", picBaseUrl + "raghav.png"),
        Developer("Devansh Agarwal", "UI/UX Designer", picBaseUrl + "yash.png"),
        Developer("Mohul Maheswari", "UI/UX Designer", picBaseUrl + "abhishek.png")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title")!!
        }
        (activity!! as MainActivity).hideCustomToolbarForLevel2Fragments()
        activity!!.search.isVisible = false
        activity!!.textView7.isVisible = false

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_commonRecyclerView_title.text = title
        backBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }
        setAdapter()
    }

    private fun setAdapter() {
        when(title) {
            "Contact Us" -> {
                recycler_commonRecyclerView.adapter = ContactUsAdapter()
                (recycler_commonRecyclerView.adapter as ContactUsAdapter).notifyDataSetChanged()
            }
            "Developers" -> {
                var devs = emptyList<Developer>()
                devs = devs.plus(developers.subList(0,4).shuffled())
                devs = devs.plus(developers.subList(4, (developers.size)))
                recycler_commonRecyclerView.adapter = DevelopersAdapter()
                (recycler_commonRecyclerView.adapter as DevelopersAdapter).developers = devs
                (recycler_commonRecyclerView.adapter as DevelopersAdapter).notifyDataSetChanged()
            }
        }
    }

}
