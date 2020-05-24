package com.anderson.bolsomovel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_produto.*
import kotlinx.android.synthetic.main.activity_produto_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*

class ProdutoActivity : AppCompatActivity() {

    private val context: Context get() = this
    var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)

        produto = intent.getSerializableExtra("produto") as Produto

        //action bar
        setSupportActionBar(toolbar)
        supportActionBar?.title = produto?.nome
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // recebe os dados do produto em activity_produto
        nomeDisciplina.text = produto?.nome
        Picasso.with(this).load(produto?.foto).fit().into(imagemDisciplina,
            object: com.squareup.picasso.Callback{
                override fun onSuccess() {}

                override fun onError() { }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_produto, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // id do item clicado
        val id = item?.itemId
        if (id == R.id.action_remover) {
            AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Deseja excluir o produto?")
                .setPositiveButton("Sim") { dialog, which ->
                    dialog.dismiss()
                    taskExcluir()
                }.setNegativeButton("Não") { dialog, which ->
                    dialog.dismiss()
                }.create().show()
        } else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskExcluir() {
        if (this.produto != null && this.produto is Produto) {
            Thread {
                ProdutoService.delete(this.produto as Produto)
                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }

}


