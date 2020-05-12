package br.com.andrecoelho.lunneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class VendedorAdapter (
    val vendedor: List<Vendedor>, val onClick: (Vendedor) -> Unit
): RecyclerView.Adapter<VendedorAdapter.VendedorViewHolder>() {

    class VendedorViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardNome: TextView
        val cardView: CardView

        init {
            cardNome = view.findViewById(R.id.cardNomev)
            cardView = view.findViewById(R.id.cardVendedor)
        }

    }

    override fun getItemCount() = this.vendedor.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendedorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_vendedor,parent, false)
        val holder = VendedorViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: VendedorViewHolder, position: Int) {
        val context = holder.itemView.context
        val vendedor = vendedor[position]

        holder.cardNome.text = vendedor.nome
        holder.itemView.setOnClickListener{onClick(vendedor)}
    }

}