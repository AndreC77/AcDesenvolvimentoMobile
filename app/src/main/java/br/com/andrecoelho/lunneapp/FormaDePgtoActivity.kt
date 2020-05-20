package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_forma_de_pgto.*
import kotlinx.android.synthetic.main.toolbar.*

class FormaDePgtoActivity : AppCompatActivity() {

    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    private val context: Context get() = this
    var formaDePgto: FormaDePagamento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma_de_pgto)

        //recuperar objeto de Forma... da intent
        formaDePgto = intent.getSerializableExtra("forma") as FormaDePagamento


        // atualizar dados da Forma
        idFormaDePgto.text = formaDePgto?.idFormaDePgto.toString()
        codigoFormaDePgto.text = formaDePgto?.codigoFormaDePgto.toString()
        descricaoFormaDePgto.text = formaDePgto?.descricaoFormaDePgto

        //colocar toobar
        setSupportActionBar(toolbar)

        //alterar titulo da ActionBar
        supportActionBar?.title = formaDePgto?.descricaoFormaDePgto
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
        val intent = Intent(this, CadastroFormaActivity::class.java)
        val id = item?.itemId
        if (id == android.R.id.home){
            finish()
        }else if(id == R.id.action_remover){
            // alerta para confirmar a remeção
            // só remove se houver confirmação positiva
            AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage("Deseja excluir Forma de Pagamento?")
                .setPositiveButton("Sim") {
                        dialog, which ->
                    dialog.dismiss()
                    taskExcluir()
                }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                }.create().show()
        }else if(id == R.id.action_atualizar){
            intent.putExtra("forma",formaDePgto)
            startActivityForResult(intent, REQUEST_REMOVE)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun taskExcluir() {
        if (this.formaDePgto != null && this.formaDePgto is FormaDePagamento) {
            // Thread para remover o Forma de Pgto
            Thread {
                FormaDePgtoService.delete(this.formaDePgto as FormaDePagamento)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }

}
