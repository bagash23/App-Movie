package com.bagas.bwamov.checkout

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

class CheckoutAdapter(private var data: List<Checkout>, private val listner: (Checkout) -> Unit) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val infaltedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(infaltedView)
    }

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listner, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle:TextView = view.findViewById(R.id.tv_kursi)
        private val tvHarga:TextView = view.findViewById(R.id.tv_harga)

        fun bindItem(data: Checkout, listner: (Checkout) -> Unit, context: Context) {

//            format rupiah
            val localID = Locale("id", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)
            tvHarga.setText(formatRupiah.format(data.harga!!.toDouble()))

            if (data.kursi!!.startsWith("Total")) {
                tvTitle.setText(data.kursi)
                tvTitle.setCompoundDrawables(null, null, null, null)
            } else {
                tvTitle.setText("Seat No. "+ data.kursi)
            }

//            pindah page sambil bawa data
            itemView.setOnClickListener {
                listner(data)
            }

        }

    }

}
