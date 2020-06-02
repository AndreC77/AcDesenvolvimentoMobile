package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro_estoque.*
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import java.lang.Long.parseLong

class CadastroEstoqueActivity : AppCompatActivity() {

    var estoque: Estoque? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_estoque)

        //recuperar objeto
        estoque = intent.getSerializableExtra("estoque") as? Estoque
        if (estoque != null) {

            insertCodEstoque.setText(estoque?.codProduto.toString())
            insertQtdEstoque.setText(estoque?.qtdEstoque.toString())

            buttonSalvarEstoque.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
                    estoque?.codProduto = parseLong(insertCodEstoque.text.toString())
                    estoque?.qtdEstoque = parseLong(insertQtdEstoque.text.toString())

                    taskPut(estoque!!)
                }
            }
            supportActionBar?.title = "Editar Estoque"
        }else{

            buttonSalvarEstoque.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
                    val estoque = Estoque()
                    estoque.codProduto = parseLong(insertCodEstoque.text.toString())
                    estoque.qtdEstoque = parseLong(insertQtdEstoque.text.toString())

                    taskAtualizar(estoque)
                }else{
                    Toast.makeText(this,"Error ao Cadastrar",Toast.LENGTH_SHORT).show()
                }
            }
            supportActionBar?.title = "Incluir Estoque"
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

    private fun taskAtualizar(estoque: Estoque) {
        // Thread para salvar a estoque
        Thread {
            var resposta = EstoqueService.save(estoque)
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

    private fun taskPut(estoque: Estoque) {
        //Thred para Atualizar a Estoque
        Thread {
            var resposta = EstoqueService.edit(estoque)
            runOnUiThread {
                if(resposta != null) {
                    Toast.makeText(this, "Error de conexão", Toast.LENGTH_SHORT).show()
                }else {
                    // após cadastrar, voltar para Tela de Estoques
                    intent = Intent(this, TelaEstoqueActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    //função pra validar os dados
    private fun validarDados() : Boolean{

        var v1 = 0; var v2 = 0

        //Validar Cod
        if(insertCodEstoque.text.toString() != ""){
            v1 = 1
        }else{
            insertCodEstoque.setError("Campo deve ser preenchido")
            insertCodEstoque.requestFocus()
        }

        //Validar Qtd
        if(insertQtdEstoque.text.toString() != ""){
            v2 = 1
        }else{
            insertQtdEstoque.setError("Campo deve ser preenchido")
            insertQtdEstoque.requestFocus()
        }

        if(v1 == 1 && v2 == 1){return true}
        return false
    }
}
