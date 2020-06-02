package br.com.andrecoelho.lunneapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_cadastro.*
import java.lang.Integer.parseInt
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
                var validarDados = validarDados()
                if (validarDados) {
                    //receber os valores do campo e setar nos atributos do objeto
                    cores?.descricaoCor = insertDescCor.text.toString()
                    cores?.codCor = parseLong(insertCodigoCor.text.toString())
                    //chamar tarefa de atualizar a cor
                    taskPut(cores!!)
                }
            }
            //Titulo na ActionBar
            supportActionBar?.title = "Editar Cor"
        }else{
            // ação do Button Para salvar a Cor
            buttonSalvarCor.setOnClickListener{
                Log.d("teste", " 3 valor = ${insertCodigoCor.text}")
                //Validacao dos dados
                var dadosValidados = validarDados()
                if(dadosValidados) {
                    val cor = Cores()
                    Log.d("teste", " 4 valor = ${insertCodigoCor.text}")
                    cor.descricaoCor = insertDescCor.text.toString()
                    cor.codCor = parseLong(insertCodigoCor.text.toString())
                    //chamar a tarefa de salvar a cor
                    taskAtualizar(cor)
                }else{
                    Log.d("teste", " 5 valor = ${insertCodigoCor.text}")
                    Toast.makeText(this,"Error em Cadastrar",Toast.LENGTH_SHORT).show()
                }
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
        Thread {
            var resposta = CoresService.save(cores)
            runOnUiThread {
                if(resposta != null) {
                    Toast.makeText(this, "Error de conexão", Toast.LENGTH_SHORT).show()
                }else {
                    // após cadastrar, voltar para activity anterior
                    finish()
                }
            }
        }.start()
    }

    private fun taskPut(cores: Cores) {
       //Thred para Atualizar a Cor
       Thread {
           var resposta = CoresService.edit(cores)
           runOnUiThread {
               if(resposta != null) {
                   Toast.makeText(this, "Error de conexão", Toast.LENGTH_SHORT).show()
               }else {
                   // após cadastrar, voltar para Tela de Cores
                   intent = Intent(this, TelaCoresActivity::class.java)
                   startActivity(intent)
                   finish()
               }
           }
       }.start()
   }

    //função pra validar os dados
    private fun validarDados() : Boolean{
        Log.d("teste", " 1 valor = ${insertCodigoCor.text}")
        var v1 = 0; var v2 = 0
        //campo cod
        var n = insertCodigoCor.text.toString()
        //Conferir se o campo cod esta vazil
        if(insertCodigoCor.text.toString() == ""){
             n = "0"
        }


        //validar a desc
        if(!TextUtils.isEmpty(insertDescCor.text.toString()) && insertDescCor.text.length > 5){
            v1 = 1
        }else{
            insertDescCor.setError("Descriçao deve ter min 5 caracteres")
            insertDescCor.requestFocus()
        }

        //validar o cod
        if( parseInt(n) > 1 && parseInt(n) <= 999 ){
            v2 = 1
        }else{
            insertCodigoCor.setError("Codigo deve estar entre 1 e 999")
            insertCodigoCor.requestFocus()
        }

        if(v1 == 1 && v2 == 1){Log.d("teste", " 2 valor = ${insertCodigoCor.text}")
            return true}
        return false
    }
}