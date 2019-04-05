package com.example.tkw.music.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tkw.music.R

class SongHolder(view:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
    val songItem = view.findViewById<ConstraintLayout>(R.id.song_item)
    val songNum = view.findViewById<TextView>(R.id.song_number)
    val palyIcon = view.findViewById<ImageView>(R.id.play_icon)
    val songName = view.findViewById<TextView>(R.id.song_name)
    val songAuthor = view.findViewById<TextView>(R.id.song_author)
}