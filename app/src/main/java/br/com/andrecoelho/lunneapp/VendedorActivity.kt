package br.com.andrecoelho.lunneapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_vendedor.*
import kotlinx.android.synthetic.main.toolbar.*

class VendedorActivity : DebugActivity() {

    private val context: Context get() = this
    var vendedor: Vendedor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)

        //recuperar objeto de Cliente da intent
        vendedor = intent.getSerializableExtra("vendedor") as Vendedor


        // atualizar dados da vendedor
        codVendedor.text = vendedor?.codVendedor.toString()
        nomeVendedor.text = vendedor?.nome
        emailVendedor.text = vendedor?.emailVendedor
        telVendedor.text = vendedor?.telefone
        celVendedor.text = vendedor?.celular
        funcionario.text = vendedor?.celular

        //colocar toobar
        setSupportActionBar(toolbar)

        //alterar titulo da ActionBar
        supportActionBar?.title = vendedor?.nome
        //up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //método sobrescrito para inflar o menu na Actionbar
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
            AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage("Deseja excluir Vendedor?")
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
        if (this.vendedor != null && this.vendedor is Vendedor) {
            // Thread para remover o vendedor
            Thread {
                VendedorService.delete(this.vendedor as Vendedor)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }
}
