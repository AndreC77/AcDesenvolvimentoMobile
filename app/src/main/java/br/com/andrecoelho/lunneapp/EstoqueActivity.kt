package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_estoque.*
import kotlinx.android.synthetic.main.toolbar.*

class EstoqueActivity : DebugActivity() {

    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    private val context: Context get() = this
    var estoque: Estoque? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estoque)

        //recuperar objeto de Estoque da intent
        estoque = intent.getSerializableExtra("estoque") as Estoque


        // atualizar dados do estoque
        idEstoque.text = estoque?.idEstoque.toString()
        codEstoque.text = estoque?.codProduto.toString()
        qtdEstoque.text = estoque?.qtdEstoque.toString()
        vendas.text = estoque?.qtdVendida.toString()
        saldoEstoque.text = estoque?.qtdSaldo.toString()


        //colocar toobar
        setSupportActionBar(toolbar)

        //alterar titulo da ActionBar
        supportActionBar?.title = "Estoque"
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
        val intent = Intent(this, CadastroEstoqueActivity::class.java)
        val id = item?.itemId
        if (id == android.R.id.home){
            finish()
        }else if(id == R.id.action_remover){
            // alerta para confirmar a remeção
            // só remove se houver confirmação positiva
            AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage("Deseja excluir Estoque?")
                .setPositiveButton("Sim") {
                        dialog, which ->
                    dialog.dismiss()
                    taskExcluir()
                }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                }.create().show()
        }else if(id == R.id.action_atualizar){
            intent.putExtra("estoque",estoque)
            startActivityForResult(intent, REQUEST_REMOVE)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun taskExcluir() {
        if (this.estoque != null && this.estoque is Estoque) {
            // Thread para remover o cliente
            Thread {
               EstoqueService.delete(this.estoque as Estoque)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }
}
