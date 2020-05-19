package com.anderson.bolsomovel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class VendedorAdapter (val vendedores: List<Vendedor>,
                       val onClick: (Vendedor) -> Unit
): RecyclerView.Adapter<VendedorAdapter.VendedoresViewHolder>() {

    class VendedoresViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardNome: TextView
        val cardView: CardView

        init {
            cardNome = view.findViewById(R.id.cardVendedoName)
            cardView = view.findViewById(R.id.cardVendedor)
        }
    }

    override fun getItemCount() = this.vendedores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendedoresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_vendedor, parent, false)

        val holder = VendedoresViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: VendedoresViewHolder, position: Int) {
        val context = holder.itemView.context

        val vendedor = vendedores[position]

        holder.cardNome.text = vendedor.nome

        //implemented progressbar here

        holder.itemView.setOnClickListener {onClick(vendedor)}
    }

}