package br.com.andrecoelho.lunneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ProdutosAdapter (
    val produtos: List<Produtos>, val onClick: (Produtos) -> Unit
): RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder>() {

    class ProdutosViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardNome: TextView
        val cardView: CardView

        init {
            cardNome = view.findViewById(R.id.cardNomep)
            cardView = view?.findViewById(R.id.cardProdutos)
        }

    }

    override fun getItemCount() = this.produtos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_produtos,parent, false)

        val holder = ProdutosViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        val context = holder.itemView.context

        val produto = produtos[position]

        holder.cardNome.text = produto.descricao

        holder.itemView.setOnClickListener{onClick(produto)}
    }

}