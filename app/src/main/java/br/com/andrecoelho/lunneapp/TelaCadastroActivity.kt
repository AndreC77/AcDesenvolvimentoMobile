package br.com.andrecoelho.lunneapp

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import java.lang.Long.parseLong

class TelaCadastroActivity : DebugActivity() {

    var cores: Cores? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        //recuperar objeto
        cores = intent.getSerializableExtra("cor") as? Cores
        if (cores != null) {
            insertCodigoCor.setText(cores?.codCor.toString())
            insertDescCor.setText(cores?.descricaoCor.toString())
            buttonSalvarCor.setOnClickListener{
                val cor = Cores()
                cor.descricaoCor = insertDescCor.text.toString()
                cor.codCor = parseLong(insertCodigoCor.text.toString())
                //cor.codCor = 102
                taskEditar(cor)
            }
            supportActionBar?.title = "Editar Cor"
        }else{
            supportActionBar?.title = "Incluir Cor"
        }

        buttonSalvarCor.setOnClickListener{
            val cor = Cores()
            cor.descricaoCor = insertDescCor.text.toString()
            cor.codCor = parseLong(insertCodigoCor.text.toString())
            //cor.codCor = 102
            taskAtualizar(cor)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun taskEditar(cores: Cores) {
        // Thread para salvar a Cor
        Thread {
            CoresService.edit(cores)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

}