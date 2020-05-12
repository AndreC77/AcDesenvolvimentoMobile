package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.activity_cor.*
import kotlinx.android.synthetic.main.activity_produtos.*
import kotlinx.android.synthetic.main.toolbar.*

class CorActivity : AppCompatActivity() {

    private val context: Context get() = this
    var cores: Cores? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cor)

        //recuperar objeto de Cores da intent
        cores = intent.getSerializableExtra("cores") as Cores


        // atualizar dados da disciplina
        idCor.text = cores?.idCor.toString()
        codigoCor.text = cores?.codCor.toString()
        descCor.text = cores?.descricaoCor

        //colocar toobar
        setSupportActionBar(toolbar)

        //alterar titulo da ActionBar
        supportActionBar?.title = cores?.descricaoCor
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
        val intent = Intent(this, TelaCadastroActivity::class.java)
        val id = item?.itemId
        if (id == android.R.id.home){
            finish()
        }else if(id == R.id.action_remover){
            // alerta para confirmar a remoção
            // só remove se houver confirmação positiva
            AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage("Deseja excluir Cor?")
                .setPositiveButton("Sim") {
                        dialog, which ->
                    dialog.dismiss()
                    taskExcluir()
                }.setNegativeButton("Não") {
                        dialog, which -> dialog.dismiss()
                }.create().show()
        }else if(id == R.id.action_atualizar){
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskExcluir() {
        if (this.cores != null && this.cores is Cores) {
            // Thread para remover o Cores
            Thread {
                CoresService.delete(this.cores as Cores)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }

}
