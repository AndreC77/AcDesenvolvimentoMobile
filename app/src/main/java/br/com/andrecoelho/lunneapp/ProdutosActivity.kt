package br.com.andrecoelho.lunneapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_produtos.*
import kotlinx.android.synthetic.main.toolbar.*

class ProdutosActivity : AppCompatActivity() {

    private val context: Context get() = this
    var produto: Produtos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        //recuperar objeto de Produto da intent
        produto = intent.getSerializableExtra("produto") as Produtos


        // atualizar dados da disciplina
        idProduto.text = produto?.idProduto.toString()
        codigo.text = produto?.codProduto.toString()
        referencia.text = produto?.ref
        descricao.text = produto?.descricao
        cor.text = produto?.cor
        nmc.text = produto?.ncm
        preco.text = produto?.preco.toString()


        //colocar toobar
        setSupportActionBar(toolbar)

        //alterar titulo da ActionBar
        supportActionBar?.title = produto?.descricao
        //up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    // método sobrescrito para inflar o menu na Actionbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main_cliente, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home){
            finish()
        }else if(id == R.id.action_remover){
            // alerta para confirmar a remeção
            // só remove se houver confirmação positiva
            AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage("Deseja excluir Produto?")
                .setPositiveButton("Sim") {
                        dialog, which ->
                    dialog.dismiss()
                    taskExcluir()
                }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                }.create().show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun taskExcluir() {
        if (this.produto != null && this.produto is Produtos) {
            // Thread para remover o produtos
            Thread {
                ProdutosService.delete(this.produto as Produtos)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }

}
