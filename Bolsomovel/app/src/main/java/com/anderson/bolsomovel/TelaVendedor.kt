package com.anderson.bolsomovel

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderson.bolsomovel.R.layout.activity_tela_vendedor
import kotlinx.android.synthetic.main.activity_tela_vendedor.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaVendedor: AppCompatActivity () {

    private val context: Context get() = this

    private var vendedores =listOf<Vendedor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_tela_vendedor)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Vendedores"

        recyclerVendedores?.layoutManager = LinearLayoutManager(context)
        recyclerVendedores?.itemAnimator = DefaultItemAnimator()
        recyclerVendedores?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskVendedores()
    }

    fun taskVendedores() {
        Thread {
            this.vendedores = VendedorService.getVendedores(context)
            runOnUiThread{
                recyclerVendedores?.adapter = VendedorAdapter(vendedores) {onClickVendedor(it)}
            }
        }.start()
    }

    fun onClickVendedor(vendedor: Vendedor) {
        Toast.makeText(context, "Clicou em ${vendedor.nome}", Toast.LENGTH_LONG).show()
    }
}