package com.example.tkw.music.holder

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.tkw.music.R

class SongListHolder(view: View):RecyclerView.ViewHolder(view){

    val songListItem1:ConstraintLayout = view.findViewById(R.id.song_list_item1)
    val songListItem2:ConstraintLayout = view.findViewById(R.id.song_list_item2)
    val songListImage1:ImageView = view.findViewById(R.id.song_list_image1)
    val songListImage2:ImageView = view.findViewById(R.id.song_list_image2)
    val songListName1:TextView = view.findViewById(R.id.song_list_name1)
    val songListName2:TextView = view.findViewById(R.id.song_list_name2)
    val songListAuthor1:TextView = view.findViewById(R.id.song_list_author1)
    val songListAuthor2:TextView = view.findViewById(R.id.song_list_author2)


}