package com.anderson.bolsomovel

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaInicial : AppCompatActivity() {

    private var produtos = listOf<Produto>()
    private val context: Context get() = this
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2
    var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        recyclerProdutos?.layoutManager = LinearLayoutManager(context)
        recyclerProdutos?.itemAnimator = DefaultItemAnimator()
        recyclerProdutos?.setHasFixedSize(true)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Produtos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        taskProdutos()
    }

    fun taskProdutos(){
        Thread {
        produtos = ProdutoService.getProdutos(context)
            runOnUiThread{
                recyclerProdutos?.adapter = ProdutoAdapter(produtos) {onClickProduto(it)}
            }
        }.start()
    }

    // tratamento do evento de clicar em uma disciplina
    fun onClickProduto(produto: Produto) {
        Toast.makeText(context, "Clicou disciplina ${produto.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId

        if (id == R.id.action_buscar) {
            Toast.makeText(this, "Clicou em Buscar", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_adicionar) {
            //evento do botao adicionar
            val intent = Intent(context, ProdutoCadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
        } else if (id == R.id.action_atualizar) {
            //evento do botao atualizar
            taskProdutos()
        } else if (id == R.id.action_adicionar) {
            val intent = Intent(context, ProdutoCadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
          //evento do botao configurar
        } else if (id == R.id.action_config) {
            val intent = Intent(context, TelaVendedor::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
        } else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // esperar o retorno do cadastro da disciplina
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE ) {
            // atualizar lista de disciplinas
            taskProdutos()
        }
    }

}

