package br.com.andrecoelho.lunneapp

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
            supportActionBar?.title = "Editar Estoque"
        }else{
            supportActionBar?.title = "Incluir Estoque"
        }

        buttonSalvarEstoque.setOnClickListener {
            val estoque = Estoque()
            estoque.codProduto = parseLong(insertCodEstoque.text.toString())
            taskAtualizar(estoque)
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
        // Thread para salvar a Cor
        Thread {
            EstoqueService.save(estoque)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }
}
