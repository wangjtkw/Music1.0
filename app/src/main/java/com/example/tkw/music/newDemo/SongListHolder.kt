package com.example.tkw.music.newDemo

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import com.example.tkw.music.ImageRelevant.LoadImage
import com.example.tkw.music.R
import com.example.tkw.music.SongListTransform
import com.example.tkw.music.holder.BaseHolder

class SongListHolder(view: View): BaseHolder(view){

    val songListItem1: ConstraintLayout = view.findViewById(R.id.song_list_item1)
    val songListItem2: ConstraintLayout = view.findViewById(R.id.song_list_item2)
    val songListImage1:ImageView = view.findViewById(R.id.song_list_image1)
    val songListImage2:ImageView = view.findViewById(R.id.song_list_image2)
    val songListName1:TextView = view.findViewById(R.id.song_list_name1)
    val songListName2:TextView = view.findViewById(R.id.song_list_name2)
    val songListAuthor1:TextView = view.findViewById(R.id.song_list_author1)
    val songListAuthor2:TextView = view.findViewById(R.id.song_list_author2)
    val handler = Handler()

    override fun bind(vararg any: Any?) {
        if (any.isEmpty())return
        val songTransform = any[0]
        if (songTransform is SongListTransform){
            songListName1.text = songTransform.name1
            songListName2.text = songTransform.name2
            songListAuthor1.text = songTransform.creator1
            songListAuthor2.text = songTransform.creator2
            LoadImage.loadImage(context,songTransform.pic1){
                songListItem1.setOnClickListener { view ->
                    val bundle = Bundle()
                    bundle.putString("id",songTransform.id1)
                    bundle.putParcelable("bitmap",it)
                    Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_fragmentSongList,bundle)
                }
                handler.post {
                    songListImage1.setImageBitmap(it)
                }
            }
            LoadImage.loadImage(context,songTransform.pic2){
                songListItem2.setOnClickListener { view ->
                    val bundle = Bundle()
                    bundle.putString("id",songTransform.id2)
                    bundle.putParcelable("bitmap",it)
                    Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_fragmentSongList,bundle)
                }
                handler.post {
                    songListImage2.setImageBitmap(it)
                }
            }


        }
    }
}