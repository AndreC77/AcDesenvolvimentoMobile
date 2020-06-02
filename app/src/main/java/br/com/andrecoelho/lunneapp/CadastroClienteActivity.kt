package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro_cliente.*
import java.lang.Long.parseLong

class CadastroClienteActivity : AppCompatActivity() {

    var clientes : Clientes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        //Mask Tel and Cell
        insertTelCliente.addTextChangedListener(Mask.mask("(##) ####-####", insertTelCliente))
        insertCelCliente.addTextChangedListener(Mask.mask("(##) #####-####", insertCelCliente))

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

            //mascara numero telefone
//            var smf = SimpleMaskFormatter("(NN) NNNNN-NNNN")
//            var mtw = MaskTextWatcher(insertTelCliente,smf)
//            insertTelCliente.addTextChangedListener(mtw)
            //fim da mascara
            salvarCliente.setOnClickListener {
                var validarDados = validarDados()
                if(validarDados) {
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
            }
            supportActionBar?.title = "Editar Cliente"
        }else{

            salvarCliente.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
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
                }else{
                    Toast.makeText(this, "Erro ao Cadastrar", Toast.LENGTH_SHORT).show()
                }
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
            var resposta = ClientesService.save(cliente)
            runOnUiThread {
                if(resposta != null) {
                    Toast.makeText(this, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }else {
                    // após cadastrar, voltar para activity anterior
                    finish()
                }
            }
        }.start()
    }

    private fun taskPut(cliente: Clientes) {
        //Thred para Atualizar a Cor
        Thread {
            var resposta = ClientesService.edit(cliente)
            runOnUiThread {
                if(resposta != null) {
                    Toast.makeText(this, "Error de conexão", Toast.LENGTH_SHORT).show()
                }else {
                    // após cadastrar, voltar para Tela de Cores
                    intent = Intent(this, TelaClienteActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    private fun validarDados() : Boolean{

        //validade dos Campos entre 1 ou 0
        var v1 = 0; var v2 = 0; var v3 = 0; var v4 = 0; var v5 = 0; var v6 = 0; var v7 = 0; var v8 = 0;
        var v9 = 0; var v10 = 0; var v11 = 0; var v12 = 0; var v13 = 0; var v14 = 0; var v15 = 0; var v16 = 0;

        //campo Cod
        if(!TextUtils.isEmpty(insertCodCliente.text.toString())){
            v1 = 1
        }else{
            insertCodCliente.setError("Campo deve ser preenchido")
            insertCodCliente.requestFocus()
        }


        //campo Regs Federal
        if(!TextUtils.isEmpty(insertRegFederal.text.toString())){
            v2 = 1
        }else{
            insertRegFederal.setError("Campo deve ser preenchido")
            insertRegFederal.requestFocus()
        }

        //campo Insc Estadual
        if(!TextUtils.isEmpty(insertInsEstadual.text.toString())){
            v16 = 1
        }else{
            insertInsEstadual.setError("Campo deve ser preenchido")
            insertInsEstadual.requestFocus()
        }

        //campo Razao Social
        if(!TextUtils.isEmpty(insertRazSocial.text.toString()) && insertRazSocial.text.length > 4 && insertRazSocial.text.length < 51){
            v3 = 1
        }else{
            insertRazSocial.setError("Razão Social tem que ter min 5 caracteres max 50")
            insertRazSocial.requestFocus()
        }

        //campo Nome Fantasia
        if(!TextUtils.isEmpty(insertNomeFantazia.text.toString()) && insertNomeFantazia.text.length > 4 && insertNomeFantazia.text.length < 51){
            v4 = 1
        }else{
            insertNomeFantazia.setError("Nome Fantasia tem que ter min 5 caracteres max 50")
            insertNomeFantazia.requestFocus()
        }

        //campo Nome Completo
        if(!TextUtils.isEmpty(insertNomeComp.text.toString()) && insertNomeComp.text.length > 4 && insertNomeComp.text.length < 51){
            v5 = 1
        }else{
            insertNomeComp.setError("Nome Completo tem que ter min 5 caracteres max 50")
            insertNomeComp.requestFocus()
        }

        //campo Email
        if(!TextUtils.isEmpty(insertEmail.text.toString())){
            v6 = 1
        }else{
            insertEmail.setError("Campo deve ser preenchido")
            insertEmail.requestFocus()
        }

        //campo Telefone
        if(!TextUtils.isEmpty(insertTelCliente.text.toString()) && insertTelCliente.text.length > 13 && insertTelCliente.text.length < 16){
            v7 = 1
        }else{
            insertTelCliente.setError("Numero de telefone incompleto")
            insertTelCliente.requestFocus()
        }

        //campo Celular
        if(!TextUtils.isEmpty(insertCelCliente.text.toString()) && insertCelCliente.text.length > 13 && insertCelCliente.text.length < 16){
            v8 = 1
        }else{
            insertCelCliente.setError("Numero de celular incompleto")
            insertCelCliente.requestFocus()
        }

        //campo Cep
        if(!TextUtils.isEmpty(insertCep.text.toString())){
            v9 = 1
        }else{
            insertCep.setError("Campo deve ser preenchido")
            insertCep.requestFocus()
        }

        //campo Rua
        if(!TextUtils.isEmpty(insertRua.text.toString())){
            v10 = 1
        }else{
            insertRua.setError("Campo deve ser preenchido")
            insertRua.requestFocus()
        }

        //campo Numero
        if(!TextUtils.isEmpty(insertNumCasa.text.toString())){
            v11 = 1
        }else{
            insertNumCasa.setError("Campo deve ser preenchido")
            insertNumCasa.requestFocus()
        }

        //campo Complemento
        if(!TextUtils.isEmpty(insertCompl.text.toString())){
            v12 = 1
        }else{
            insertCompl.setError("Campo deve ser preenchido")
            insertCompl.requestFocus()
        }

        //campo Bairro
        if(!TextUtils.isEmpty(insertBairro.text.toString())){
            v13 = 1
        }else{
            insertBairro.setError("Campo deve ser preenchido")
            insertBairro.requestFocus()
        }

        //campo Municipio
        if(!TextUtils.isEmpty(insertMunic.text.toString())){
            v14 = 1
        }else{
            insertMunic.setError("Campo deve ser preenchido")
            insertMunic.requestFocus()
        }

        //campo Uf
        if(!TextUtils.isEmpty(insertUf.text.toString())){
            v15 = 1
        }else{
            insertUf.setError("Campo deve ser preenchido")
            insertUf.requestFocus()
        }

        if(v1 == 1 && v2 == 1 && v3 == 1 && v4 == 1 && v5 == 1 && v6 == 1 && v7 == 1 &&
            v8 == 1 && v9 == 1 && v10 == 1 && v11 == 1 && v12 == 1 && v13 == 1 && v14 == 1 && v15 == 1 && v16 == 1){return true}

        return false
    }
}
