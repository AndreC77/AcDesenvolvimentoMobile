package br.com.andrecoelho.lunneapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import kotlinx.android.synthetic.main.activity_tela_cadastro.view.*
import java.lang.Long.parseLong

class TelaCadastroActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        buttonSalvarCor.setOnClickListener{
            val cor = Cores()
            cor.idCor = 0
            cor.descricaoCor = insertDescCor.text.toString()
            cor.codCor = parseLong(insertCodigoCor.text.toString())
            //cor.codCor = 102
            taskAtualizar(cor)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Incluir Cor"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskAtualizar(cores: Cores) {
        // Thread para salvar a Cor
        Thread {
            CoresService.save(cores)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

}