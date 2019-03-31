package com.example.tkw.music.holder

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.tkw.music.R

class SongHolder(view:View):RecyclerView.ViewHolder(view){
    val songItem = view.findViewById<ConstraintLayout>(R.id.song_item)
    val songNum = view.findViewById<TextView>(R.id.song_number)
    val palyIcon = view.findViewById<ImageView>(R.id.play_icon)
    val songName = view.findViewById<TextView>(R.id.song_name)
    val songAuthor = view.findViewById<TextView>(R.id.song_author)
}