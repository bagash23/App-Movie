package com.bagas.bwamov.home.tiket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagas.bwamov.R
import com.bagas.bwamov.model.Checkout
import java.text.NumberFormat
import java.util.Locale

class TiketAdapter(private var data: List<Checkout>, private val listner: (Checkout) -> Unit) : RecyclerView.Adapter<TiketAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiketAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val infaltedView = layoutInflater.inflate(R.layout.row_item_checkout_white, parent, false)
        return ViewHolder(infaltedView)
    }

    override fun onBindViewHolder(holder: TiketAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listner, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle:TextView = view.findViewById(R.id.tv_kursi)

        fun bindItem(data: Checkout, listner: (Checkout) -> Unit, context: Context) {


            tvTitle.setText("Seat No. "+data.kursi)

//            pindah page sambil bawa data
            itemView.setOnClickListener {
                listner(data)
            }

        }

    }

}
