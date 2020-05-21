package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

                estoque?.codProduto = parseLong(insertCodEstoque.text.toString())
                estoque?.qtdEstoque = parseLong(insertQtdEstoque.text.toString())

                taskPut(estoque!!)
            }
            supportActionBar?.title = "Editar Estoque"
        }else{

            buttonSalvarEstoque.setOnClickListener {

                val estoque = Estoque()
                estoque.codProduto = parseLong(insertCodEstoque.text.toString())
                estoque.qtdEstoque = parseLong(insertQtdEstoque.text.toString())

                taskAtualizar(estoque)
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
            EstoqueService.save(estoque)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

    private fun taskPut(estoque: Estoque) {
        //Thred para Atualizar a Estoque
        Thread {
            EstoqueService.edit(estoque)
            runOnUiThread {
                // após cadastrar, voltar para Tela de Estoques
                intent = Intent(this, TelaEstoqueActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}
