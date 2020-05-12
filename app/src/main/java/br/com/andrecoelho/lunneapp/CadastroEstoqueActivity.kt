package br.com.andrecoelho.lunneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_cadastro_estoque.*
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import java.lang.Long.parseLong

class CadastroEstoqueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_estoque)

        val estoque = Estoque()

        buttonSalvarEstoque.setOnClickListener {
            estoque.codProduto = parseLong(insertCodEstoque.text.toString())
            taskAtualizar(estoque)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Incluir Estoque"
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
