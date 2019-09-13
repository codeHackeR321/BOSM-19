package com.dvm.appd.bosm.dbg.more

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.more.dataClasses.Developer
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.row_developer.view.*

class DevelopersAdapter : RecyclerView.Adapter<DevelopersAdapter.DeveloperVHolder>() {

   var developers: List<Developer> = emptyList()
    private val baseImageLink = "https://www.bits-bosm.org/"
    override fun getItemCount() = developers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DeveloperVHolder(inflater.inflate(R.layout.row_developer, parent, false))
    }

    override fun onBindViewHolder(holder: DeveloperVHolder, position: Int) {
        val developer = developers[position]
        FirebaseStorage.getInstance().reference.child("${developers[position].name}.jpg").downloadUrl.addOnSuccessListener {
            Glide.with(holder.itemView.context!!).load(it).placeholder(R.drawable.ic_outline_profile_identity_24px).circleCrop().into(holder.picIMG)
        }
        if(position>=4||position<=8){
            Log.d("check","${baseImageLink}/img/developers/${developers[position]}.jpg")
            Glide.with(holder.itemView.context!!).load("${baseImageLink}/img/developers/${developers[position].name.substringBefore(" ").toLowerCase()}.jpg").placeholder(R.drawable.ic_outline_profile_identity_24px).circleCrop()
                .into(holder.picIMG)
        }
        holder.nameLBL.text = developer.name
        holder.roleLBL.text = developer.role

    }

    class DeveloperVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val nameLBL: TextView = rootPOV.nameLBL
        val roleLBL: TextView = rootPOV.roleLBL
        val picIMG: ImageView = rootPOV.picIMG
    }
}