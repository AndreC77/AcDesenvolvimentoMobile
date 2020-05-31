package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro_forma.*
import kotlinx.android.synthetic.main.activity_cadastro_vendedor.*
import java.lang.Integer.parseInt
import java.lang.Long.parseLong


class CadastroVendedorActivity : DebugActivity() {

    var vendedor : Vendedor? = null
    var par1 = arrayOf("Sim", "Não")
    var selecao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_vendedor)

        val spinnerV = this.findViewById<Spinner>(R.id.spinnerFuncionario)
        //Adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, this.par1)

        spinnerV.adapter = adapter
        spinnerV.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //recuperar objeto
        vendedor = intent.getSerializableExtra("vendedor") as? Vendedor
        if (vendedor != null) {

            insertNomeVendedor.setText(vendedor?.nome.toString())
            insertEmailVendedor.setText(vendedor?.emailVendedor.toString())
            insertTelVendedor.setText(vendedor?.telefone.toString())
            insertCelVendedor.setText(vendedor?.celular.toString())
            insertCodVendedor.setText(vendedor?.codVendedor.toString())

            buttonSalvarVendedor.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
                    vendedor?.nome = insertNomeVendedor.text.toString()
                    vendedor?.emailVendedor = insertEmailVendedor.text.toString()
                    vendedor?.senha = insertSenhaVendedor.text.toString()
                    vendedor?.telefone = insertTelVendedor.text.toString()
                    vendedor?.celular = insertCelVendedor.text.toString()
                    vendedor?.codVendedor = parseLong(insertCodVendedor.text.toString())
                    vendedor?.funcionario = spinnerFuncionario.selectedItem.toString()
                    taskPut(vendedor!!)
                }
            }
            supportActionBar?.title = "Editar Vendedor"
        }else{

            buttonSalvarVendedor.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
                    var vendedor = Vendedor()
                    vendedor.nome = insertNomeVendedor.text.toString()
                    vendedor.emailVendedor = insertEmailVendedor.text.toString()
                    vendedor.senha = insertSenhaVendedor.text.toString()
                    vendedor.telefone = insertTelVendedor.text.toString()
                    vendedor.celular = insertCelVendedor.text.toString()
                    vendedor.codVendedor = parseLong(insertCodVendedor.text.toString())
                    vendedor.funcionario = spinnerFuncionario.selectedItem.toString()

                    taskAtualizar(vendedor)
                }else{
                    Toast.makeText(this,"Erro ao Cadastrar",Toast.LENGTH_SHORT).show()
                }
            }
            supportActionBar?.title = "Incluir Vendedor"
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

    private fun taskAtualizar(vendedor: Vendedor) {
        // Thread para salvar a Cor
        Thread {
            VendedorService.save(vendedor)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()
    }

    private fun taskPut(vendedor: Vendedor) {
        //Thred para Atualizar o Vendedor
        Thread {
            VendedorService.edit(vendedor)
            runOnUiThread {
                // após cadastrar, voltar para Tela de Vendedores
                intent = Intent(this, TelaVendedorActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun validarDados() : Boolean {
        var v1 = 0; var v2 = 0; var v3 = 0; var v4 = 0; var v5 = 0; var v6 = 0; var v7 = 0

        //campo nome
        if(!TextUtils.isEmpty(insertNomeVendedor.text.toString()) && insertNomeVendedor.text.length > 4 && insertNomeVendedor.text.length < 51){
            v1 = 1
        }else{
            insertNomeVendedor.setError("Nome tem que ter min 5 caracteres max 50 ")
            insertNomeVendedor.requestFocus()
        }

        //campo email
        if(!TextUtils.isEmpty(insertEmailVendedor.text.toString()) && insertEmailVendedor.text.length > 4 && insertEmailVendedor.text.length < 41){
            v2 = 1
        }else{
            insertEmailVendedor.setError("Email tem que ter min 5 caracteres max 40 ")
            insertEmailVendedor.requestFocus()
        }

        //campo senha
        if(!TextUtils.isEmpty(insertSenhaVendedor.text.toString())){
            v3 = 1
        }else{
            insertSenhaVendedor.setError("Campo deve ser preenchido")
            insertSenhaVendedor.requestFocus()
        }

        //campo confirmar senha
        if(insertSenhaVendedor.text.toString() == confirmarSenha.text.toString()){
            v4 = 1
        }else{
            confirmarSenha.setError("Senha diferente!")
            confirmarSenha.requestFocus()
        }

        //campo telefone
        if(!TextUtils.isEmpty(insertTelVendedor.text.toString())){
            v5 = 1
        }else{
            insertTelVendedor.setError("Campo deve ser preenchido")
            insertTelVendedor.requestFocus()
        }

        //campo celular
        if(!TextUtils.isEmpty(insertCelVendedor.text.toString())){
            v6 = 1
        }else{
            insertCelVendedor.setError("Campo deve ser preenchido")
            insertCelVendedor.requestFocus()
        }

        //campo codigo
        var n = insertCodVendedor.text.toString()
        if(n == ""){n = "0"}
        if(!TextUtils.isEmpty(insertCodVendedor.text.toString()) && parseInt(n) > 0 && parseInt(n) < 1000){
            v7 = 1
        }else{
            insertCodVendedor.setError("Codigo deve estar entre 1 e 999")
            insertCodVendedor.requestFocus()
        }

        if(v1 == 1 && v2 == 1 && v3 == 1 && v4 == 1 && v5 == 1 && v6 == 1 && v7 == 1){return true}

        return false
    }

}
