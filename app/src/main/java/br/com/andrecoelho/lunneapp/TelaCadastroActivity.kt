package br.com.andrecoelho.lunneapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            //Recuperar o objeto nos campos
            insertCodigoCor.setText(cores?.codCor.toString())
            insertDescCor.setText(cores?.descricaoCor.toString())
            //Ação depois de Apertar o Button para edidar a Cor
            buttonSalvarCor.setOnClickListener{
                //receber os valores do campo e setar nos atributos do objeto
                cores?.descricaoCor = insertDescCor.text.toString()
                cores?.codCor = parseLong(insertCodigoCor.text.toString())
                //chamar tarefa de atualizar a cor
                taskPut(cores!!)
            }
            //Titulo na ActionBar
            supportActionBar?.title = "Editar Cor"
        }else{
            // ação do Button Para salvar a Cor
            buttonSalvarCor.setOnClickListener{
                val cor = Cores()
                cor.descricaoCor = insertDescCor.text.toString()
                cor.codCor = parseLong(insertCodigoCor.text.toString())
                //chamar a tarefa de salvar a cor
                taskAtualizar(cor)
            }

            supportActionBar?.title = "Incluir Cor"
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
        Log.d("WS_LMSApp", cores.toJson())
        Thread {
            CoresService.save(cores)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

   private fun taskPut(cores: Cores) {
       //Thred para Atualizar a Cor
       Thread {
           CoresService.edit(cores)
           runOnUiThread {
               // após cadastrar, voltar para Tela de Cores
               intent = Intent(this, TelaCoresActivity::class.java)
               startActivity(intent)
               finish()
           }
       }.start()
   }
}