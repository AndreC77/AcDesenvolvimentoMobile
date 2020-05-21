package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_cadastro_cliente.*
import java.lang.Long.parseLong

class CadastroClienteActivity : AppCompatActivity() {

    var clientes : Clientes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        //recuperar objeto
        clientes = intent.getSerializableExtra("clientes") as? Clientes
        if (clientes != null){

            //cliente
            insertCodCliente.setText(clientes?.codCliente.toString())
            insertRegFederal.setText(clientes?.registroFederal.toString())
            insertInsEstadual.setText(clientes?.inscricaoEstadual.toString())
            insertRazSocial.setText(clientes?.razaoSocial.toString())
            insertNomeFantazia.setText(clientes?.nomeFantazia.toString())
            insertNomeComp.setText(clientes?.nomeCompleto.toString())
            insertEmail.setText(clientes?.email.toString())
            insertTelCliente.setText(clientes?.numeroTelefone.toString())
            insertCelCliente.setText(clientes?.numeroCelular.toString())
            //Endereco
            insertCep.setText(clientes?.enderecoEntity?.cep.toString())
            insertRua.setText(clientes?.enderecoEntity?.logradouro.toString())
            insertNumCasa.setText(clientes?.enderecoEntity?.numero.toString())
            insertCompl.setText(clientes?.enderecoEntity?.complemento.toString())
            insertBairro.setText(clientes?.enderecoEntity?.bairro.toString())
            insertMunic.setText(clientes?.enderecoEntity?.localidade.toString())
            insertUf.setText(clientes?.enderecoEntity?.uf.toString())
            insertIbge.setText(clientes?.enderecoEntity?.ibge.toString())


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

                clientes?.codCliente = parseLong(insertCodCliente.text.toString())
                clientes?.registroFederal = insertRegFederal.text.toString()
                clientes?.inscricaoEstadual = insertInsEstadual.text.toString()
                clientes?.razaoSocial = insertRazSocial.text.toString()
                clientes?.nomeFantazia = insertNomeFantazia.text.toString()
                clientes?.nomeCompleto = insertNomeComp.text.toString()
                clientes?.email = insertEmail.text.toString()
                clientes?.numeroTelefone = insertTelCliente.text.toString()
                clientes?.numeroCelular = insertCelCliente.text.toString()
                clientes?.enderecoEntity = endereco

                taskPut(clientes!!)
            }
            supportActionBar?.title = "Editar Cliente"
        }else{

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
            supportActionBar?.title = "Incluir Cliente"
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

    private fun taskPut(cliente: Clientes) {
        //Thred para Atualizar a Cor
        Thread {
            ClientesService.edit(cliente)
            runOnUiThread {
                // após cadastrar, voltar para Tela de Cores
                intent = Intent(this, TelaClienteActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}
