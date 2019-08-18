package com.dvm.appd.bosm.dbg.events.view.adapters

import android.media.AudioTimestamp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.events.data.room.dataclasses.SportsData
import kotlinx.android.synthetic.main.card_sports_vertical_2.view.*
import kotlinx.android.synthetic.main.cards_sports_vertical_1.view.*
import kotlinx.android.synthetic.main.cards_sports_vertical_1.view.textViewDate
import kotlinx.android.synthetic.main.cards_sports_vertical_1.view.textViewRound
import kotlinx.android.synthetic.main.cards_sports_vertical_1.view.textViewTime
import kotlinx.android.synthetic.main.cards_sports_vertical_1.view.textViewVenue
import java.text.SimpleDateFormat
import java.util.*

class SportsDataAdapter(/*private val listener: OnMarkFavouriteClicked*/) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var sportData: List<SportsData> = emptyList()
    var genderSelected=""

    /* interface OnMarkFavouriteClicked{
         fun updateIsFavourite(eventId: String, favouriteMark: Int)
     }*/

    class SportsDataViewHolder1(view: View) : RecyclerView.ViewHolder(view) {

        val round: TextView = view.textViewRound
        val team1: TextView = view.textViewTeam1
        val team2: TextView = view.textViewTeam2
        val score1: TextView = view.textViewScore1
        val score2: TextView = view.textViewScore2
        val date: TextView = view.textViewDate
        val time: TextView = view.textViewTime
        val venue: TextView = view.textViewVenue
        val winner1: TextView = view.textViewWinner1_1
        //val markFav: Button = view.markFav

    }


    class SportsDataViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

        val round: TextView = view.textViewRound
        val roundType: TextView = view.textViewRoundType
        val winner1: TextView = view.textViewWinner1
        val winner2: TextView = view.textViewWinner2
        val winner3: TextView = view.textViewWinner3
        val date: TextView = view.textViewDate
        val time: TextView = view.textViewTime
        val venue: TextView = view.textViewVenue
        //val markFav: Button = view.markFav

    }

    override fun getItemViewType(position: Int): Int {
        return sportData[position].layout

//        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View?
        //Here user no. is to be placed
        if (viewType == 1) {

            view = LayoutInflater.from(parent.context).inflate(R.layout.cards_sports_vertical_1, parent, false) as View

            return SportsDataViewHolder1(view)
        } else {

            view = LayoutInflater.from(parent.context).inflate(R.layout.card_sports_vertical_2, parent, false) as View
            return SportsDataViewHolder2(view)
        }
        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_sports_vertical_1, parent, false)
        return SportsDataViewHolder(view)
   */
    }

    override fun getItemCount(): Int = sportData.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (/*sportData[position].gender.equals(genderSelected)*/ true)
        {
            if (holder.itemViewType == 1) {
                val holder1: SportsDataViewHolder1 = holder as SportsDataViewHolder1

                if (sportData[position].round.isNullOrEmpty())
                    holder1.round.text = "Round"
                else
                    holder1.round.text = sportData[position].round


                if (sportData[position].isScore)
                {
                    holder1.score1.text=sportData[position].score_1
                    holder1.score2.text=sportData[position].score_2
                }
                else
                {
                    holder1.score1.visibility=View.GONE
                    holder1.score1.visibility=View.GONE
                }

                if(sportData[position].winner1.isNullOrEmpty())
                    holder1.winner1.visibility=View.GONE
                else
                    holder1.winner1.text="Winner: ${sportData[position].winner1}"

                holder1.date.text = getDate(timestamp = sportData[position].time)
                holder1.time.text = getTime(timestamp = sportData[position].time)
                holder1.venue.text=sportData[position].venue
                holder1.team1.text=sportData[position].team_1
                holder1.team2.text=sportData[position].team_2


            }
            else if (holder.itemViewType == 2) {
                val holder2: SportsDataViewHolder2 = holder as SportsDataViewHolder2

                if (sportData[position].round.isNullOrEmpty())
                    holder2.round.text = "Round"
                else
                    holder2.round.text = sportData[position].round

                if (sportData[position].round_type.isNullOrEmpty())
                    holder2.roundType.visibility=View.GONE
                else
                    holder2.roundType.text=sportData[position].round_type

                holder2.date.text = getDate(timestamp = sportData[position].time)
                holder2.time.text = getTime(timestamp = sportData[position].time)
                holder2.venue.text=sportData[position].venue

                if (sportData[position].winner1.isNullOrEmpty())
                   holder2.winner1.visibility=View.GONE
                else
                    holder2.winner1.text="1: ${sportData[position].winner1}"

                if (sportData[position].winner2.isNullOrEmpty())
                    holder2.winner2.visibility=View.GONE
                else
                    holder2.winner2.text="2: "+ sportData[position].winner2

                if (sportData[position].winner3.isNullOrEmpty())
                    holder2.winner3.visibility=View.GONE
                else
                    holder2.winner3.text="3: "+sportData[position].winner3

            }
        }
    }

    private fun getDate(timestamp: Long): String {

        val sdf = java.text.SimpleDateFormat("d MMM")
        val date = java.util.Date(timestamp*1000)
        return sdf.format(date)
    }

    private fun getTime(timestamp: Long): String {

        val sdf = java.text.SimpleDateFormat("h:mm a")
        val date = java.util.Date(timestamp*1000)
        return sdf.format(date)
    }
}