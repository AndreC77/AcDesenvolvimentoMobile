package com.anderson.bolsomovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_produto_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*

class ProdutoCadastroActivity : AppCompatActivity() {

    var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_cadastro)
        //setTitle("Novo Produto")

        /*toolbar*/
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Novo produto"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        salvarProduto.setOnClickListener {
            val produto = Produto()
            produto.nome = nomeProduto.text.toString()
            produto.foto = fotoProduto.text.toString()
            produto.qtd = qtdProduto.text.toString()

            taskAtualizar(produto)
        }
    }

    private fun taskAtualizar(produto: Produto) {
        Thread {
            ProdutoService.save(produto)
            runOnUiThread{
                finish()
            }
        }.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) { finish()}

        return super.onOptionsItemSelected(item)
    }


}


