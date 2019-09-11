package com.dvm.appd.bosm.dbg.elas.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.elas.model.retrofit.PlayerRankingResponse
import kotlinx.android.synthetic.main.card_leaderboard_elas.view.*

class ELasLeaderoardAdapter : RecyclerView.Adapter<ELasLeaderoardAdapter.ElasLeaderboardViewHolder>() {

    var leaderboardList: List<PlayerRankingResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElasLeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_leaderboard_elas, parent, false)
        return ElasLeaderboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }

    override fun onBindViewHolder(holder: ElasLeaderboardViewHolder, position: Int) {
        holder.playerName.text = leaderboardList[position].Name
        holder.textRank.text = leaderboardList[position].Rank.toString()
        holder.playerPoints.text = "${leaderboardList[position].Points.toString()} Pts"
    }

    inner class ElasLeaderboardViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textRank = view.text_leaderboard_ranking
        val playerPoints = view.text_leaderboard_points
        val playerName = view.text_leaderboard_name
    }

}