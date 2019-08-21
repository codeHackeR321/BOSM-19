package com.dvm.appd.bosm.dbg.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.more.dataClasses.Contact
import kotlinx.android.synthetic.main.row_contact_us.view.*


class ContactUsAdapter : RecyclerView.Adapter<ContactUsAdapter.ContactVHolder>() {

    private val contacts = listOf(
        Contact("Mehul Jain", "For medical emergencies", "+91-8427878749", "N/A", "N/A"),
        Contact("Satyansh Rai", "President, Student Union", "+91-9151178228", "president@pilani.bits-pilani.ac.in", "https://bits-apogee.org/3f48d8dff7707dc4d5eff6453b7db8aa.jpg"),
        Contact("Aakash Singh", "General Secretary, Student Union", "+91-9468923617", "gensec@pilani.bits-pilani.ac.in", "https://bits-apogee.org/d2922f9db921923834c804c1705beacc.jpg"),
        Contact("Megh Thakkar", "For app related queries", "+91-9829799877", "webmaster@bits-apogee.org", "https://bits-apogee.org/48524dae9913a21ac36b617e1ce44058.jpg"),
        Contact("Parv Panthari", "Registrations and Correspondence", "+91-9462011235", "pcr@bits-apogee.org", "https://bits-apogee.org/561b0cde1229afdda21420f97ac9ff41.jpg"),
        Contact("Anushka Pathak", "Sponsorship and Marketing", "+91-7795025103", "sponsorship@bits-apogee.org", "https://bits-apogee.org/6fcb439c24576a5236f38cbf1342f900.jpg"),
        Contact("Apoorv Saxena", "Registration, Events and Projects", "+91-7239805667", "controls@bits-apogee.org", "https://bits-apogee.org/eeb0c0443872cd1f96e4411474110509.jpg"),
        Contact("Yatharth Singh", "Reception and Accomodation", "+91-9971393939", "recnacc@bits-apogee.org", "https://bits-apogee.org/ae5a18b484ba677cf3589d3cd581db3d.jpg"),
        Contact("Aditya Pawar", "Publicity and Online Partnerships", "+91-9829971666", "adp@bits-apogee.org", "https://bits-apogee.org/0378ed88538d1beb8e939902a59b8bbc.jpg"),
        Contact("Anirudh Singla", "Guest Lectures and Paper Presentation", "+91-9818536680", "pep@bits-apogee.org", "https://bits-apogee.org/65ceaf2463bc5cd5c4c7a15f9f3b9981.jpg")
    )


    override fun getItemCount() = contacts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactVHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactVHolder(inflater.inflate(R.layout.row_contact_us, parent, false))
    }

    override fun onBindViewHolder(holder: ContactVHolder, position: Int) {
        val contact = contacts[position]

        holder.nameLBL.text = contact.name
        holder.roleLBL.text = contact.role
        holder.phoneLBL.text = contact.phone
        holder.emailLBL.text = contact.email

    }


    class ContactVHolder(rootPOV: View) : RecyclerView.ViewHolder(rootPOV) {

        val picIMG: ImageView = rootPOV.picIMG
        val nameLBL: TextView = rootPOV.nameLBL
        val roleLBL: TextView = rootPOV.roleLBL
        val phoneLBL: TextView = rootPOV.phoneLBL
        val emailLBL: TextView = rootPOV.emailLBL
    }
}