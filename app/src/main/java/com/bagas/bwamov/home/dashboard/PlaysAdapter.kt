package com.bagas.bwamov.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagas.bwamov.R
import com.bagas.bwamov.model.Plays
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PlaysAdapter(private var data: List<Plays>, private val listner: (Plays) -> Unit) : RecyclerView.Adapter<PlaysAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaysAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val infaltedView = layoutInflater.inflate(R.layout.row_item_play, parent, false)
        return ViewHolder(infaltedView)
    }

    override fun onBindViewHolder(holder: PlaysAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listner, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle:TextView = view.findViewById(R.id.tv_kursi)

        private val tvImage:ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Plays, listner: (Plays) -> Unit, context: Context) {
            tvTitle.setText(data.nama)

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(tvImage)

//            pindah page sambil bawa data
            itemView.setOnClickListener {
                listner(data)
            }

        }

    }

}
