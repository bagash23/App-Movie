package com.bagas.bwamov.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagas.bwamov.R
import com.bagas.bwamov.model.Film
import com.bumptech.glide.Glide

class NowPlayingAdapter(private var data: List<Film>, private val listner: (Film) -> Unit) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val infaltedView = layoutInflater.inflate(R.layout.row_item_now_playing, parent, false)
        return ViewHolder(infaltedView)
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listner, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle:TextView = view.findViewById(R.id.tv_kursi)
        private val tvGenre:TextView = view.findViewById(R.id.tv_genre)
        private val tvRate:TextView = view.findViewById(R.id.tv_rate)

        private val tvImage:ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Film, listner: (Film) -> Unit, context: Context) {
            tvTitle.setText(data.judul)
            tvGenre.setText(data.genre)
            tvRate.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(tvImage)

//            pindah page sambil bawa data
            itemView.setOnClickListener {
                listner(data)
            }

        }

    }

}
