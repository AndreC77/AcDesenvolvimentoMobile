package br.com.andrecoelho.lunneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class FormaDePgtoAdapter (
    val forma: List<FormaDePagamento>, val onClick: (FormaDePagamento) -> Unit
): RecyclerView.Adapter<FormaDePgtoAdapter.FormaViewHolder>() {

    class FormaViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardNome: TextView
        val cardView: CardView

        init {
            cardNome = view.findViewById(R.id.cardNomef)
            cardView = view.findViewById(R.id.cardFormaDePgto)
        }
    }

    override fun getItemCount() = this.forma.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_forma,parent, false)
        val holder = FormaViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: FormaViewHolder, position: Int) {
        val context = holder.itemView.context
        val forma = forma[position]

        holder.cardNome.text = forma.descricaoFormaDePgto
        holder.itemView.setOnClickListener{onClick(forma)}
    }
}