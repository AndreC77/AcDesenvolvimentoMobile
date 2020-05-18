package com.anderson.bolsomovel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_produto_cadastro.*

class ProdutoCadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_cadastro)
        setTitle("Novo Produto")

        salvarProduto.setOnClickListener {
            val produto = Produto()
            produto.nome = nomeProduto.text.toString()
            produto.foto = fotoProduto.text.toString()
            produto.qtd = qtdProduto.text.toString()

            taskAtualizar(produto)
        }
    }
    private fun taskAtualizar(produto: Produto) {
        Thread {
            ProdutoService.save(produto)
            runOnUiThread{
                finish()
            }
        }.start()
    }
}

