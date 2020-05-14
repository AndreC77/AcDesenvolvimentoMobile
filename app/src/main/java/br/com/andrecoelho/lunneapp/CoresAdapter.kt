package br.com.andrecoelho.lunneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CoresAdapter (
    val cores: List<Cores>, val onClick: (Cores) -> Unit
): RecyclerView.Adapter<CoresAdapter.CoresViewHolder>() {

    class CoresViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardNome: TextView
        val cardView: CardView

        init {
            cardNome = view.findViewById(R.id.cardNomec)
            cardView = view.findViewById(R.id.cardCores)
        }
    }

    override fun getItemCount() = this.cores.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cores,parent, false)
        val holder = CoresViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: CoresViewHolder, position: Int) {
        val context = holder.itemView.context

        val cores = cores[position]

        holder.cardNome.text = cores.descricaoCor

        holder.itemView.setOnClickListener{onClick(cores)}
    }

}