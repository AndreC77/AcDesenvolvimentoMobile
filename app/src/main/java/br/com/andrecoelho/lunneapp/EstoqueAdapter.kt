package br.com.andrecoelho.lunneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class EstoqueAdapter (
    val estoque: List<Estoque>, val onClick: (Estoque) -> Unit
): RecyclerView.Adapter<EstoqueAdapter.EstoqueViewHolder>() {

    class EstoqueViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardNome: TextView
        val cardView: CardView
        init {
            cardNome = view.findViewById(R.id.cardNomee)
            cardView = view.findViewById(R.id.cardEstoque)
        }
    }

    override fun getItemCount() = this.estoque.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstoqueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_estoque,parent, false)
        val holder = EstoqueViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: EstoqueViewHolder, position: Int) {
        val context = holder.itemView.context

        val estoque = estoque[position]
        val x = estoque.idEstoque.toString()

        holder.cardNome.text = "Estoque: $x"
        holder.itemView.setOnClickListener{onClick(estoque)}
    }

}