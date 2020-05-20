package br.com.andrecoelho.lunneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_cadastro_cliente.*
import java.lang.Long.parseLong

class CadastroClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        salvarCliente.setOnClickListener {
            //endereco
            val endereco = Endereco()
            endereco.cep = insertCep.text.toString()
            endereco.localidade = insertMunic.text.toString()
            endereco.numero = insertNumCasa.text.toString()
            endereco.complemento = insertCompl.text.toString()
            endereco.bairro = insertBairro.text.toString()
            endereco.logradouro = insertRua.text.toString()
            endereco.uf = insertUf.text.toString()
            endereco.ibge = parseLong(insertIbge.text.toString())

            //cliente
            val cliente = Clientes()
            cliente.codCliente = parseLong(insertCodCliente.text.toString())
            cliente.registroFederal = insertRegFederal.text.toString()
            cliente.inscricaoEstadual = insertInsEstadual.text.toString()
            cliente.razaoSocial = insertRazSocial.text.toString()
            cliente.nomeFantazia = insertNomeFantazia.text.toString()
            cliente.nomeCompleto = insertNomeComp.text.toString()
            cliente.email = insertEmail.text.toString()
            cliente.numeroTelefone = insertTelCliente.text.toString()
            cliente.numeroCelular = insertCelCliente.text.toString()
            cliente.enderecoEntity = endereco

            taskAtualizar(cliente)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Incluir Cliente"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskAtualizar(cliente: Clientes) {
        // Thread para salvar a Cor
        Thread {
            ClientesService.save(cliente)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }
}
