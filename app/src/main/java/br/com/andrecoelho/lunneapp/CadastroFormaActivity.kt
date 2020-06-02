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
import androidx.core.text.set
import kotlinx.android.synthetic.main.activity_cadastro_forma.*
import java.lang.Integer.parseInt
import java.lang.Long.parseLong

class CadastroFormaActivity : DebugActivity() {

    var forma: FormaDePagamento? = null

    var par1 = arrayOf(0, 15, 30, 45, 60, 75, 90, 105, 120)
    var selecao1 = 0
    var selecao2 = 0
    var selecao3 = 0
    var selecao4 = 0
    var selecao5 = 0
    var selecao6 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_cadastro_forma)

        //Spinners
        val spinnerP1 = this.findViewById<Spinner>(R.id.spinnerParcela1)
        val spinnerP2 = this.findViewById<Spinner>(R.id.spinnerParcela2)
        val spinnerP3 = this.findViewById<Spinner>(R.id.spinnerParcela3)
        val spinnerP4 = this.findViewById<Spinner>(R.id.spinnerParcela4)
        val spinnerP5 = this.findViewById<Spinner>(R.id.spinnerParcela5)
        val spinnerP6 = this.findViewById<Spinner>(R.id.spinnerParcela6)
        //Adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, this.par1)

        spinnerP1.adapter = adapter
        spinnerP1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao1 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerP2.adapter = adapter
        spinnerP2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao2 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerP3.adapter = adapter
        spinnerP3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao3 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerP4.adapter = adapter
        spinnerP4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao4 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerP5.adapter = adapter
        spinnerP5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao5 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerP6.adapter = adapter
        spinnerP6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selecao6 = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //recuperar objeto
        forma = intent.getSerializableExtra("forma") as? FormaDePagamento
        if (forma != null) {

            insertCodForma.setText(forma?.codigoFormaDePgto.toString())
            insertDescricao.setText(forma?.descricaoFormaDePgto.toString())

            buttonSalvarForma.setOnClickListener {

                var validarDados = validarDados()
                if(validarDados) {
                    forma?.codigoFormaDePgto = parseLong(insertCodForma.text.toString())
                    forma?.descricaoFormaDePgto = insertDescricao.text.toString()
                    forma?.parcela1 = parseInt(spinnerP1.selectedItem.toString())
                    forma?.parcela2 = parseInt(spinnerP2.selectedItem.toString())
                    forma?.parcela3 = parseInt(spinnerP3.selectedItem.toString())
                    forma?.parcela4 = parseInt(spinnerP4.selectedItem.toString())
                    forma?.parcela5 = parseInt(spinnerP5.selectedItem.toString())
                    forma?.parcela6 = parseInt(spinnerP6.selectedItem.toString())

                    taskPut(forma!!)
                }
            }
            supportActionBar?.title = "Editar Forma de Pagamento"
        }else{
            //atribuicao
            buttonSalvarForma.setOnClickListener {
                var validarDados = validarDados()
                val forma = FormaDePagamento()
                if(validarDados) {
                    forma.codigoFormaDePgto = parseLong(insertCodForma.text.toString())
                    forma.descricaoFormaDePgto = insertDescricao.text.toString()
                    forma.parcela1 = parseInt(spinnerP1.selectedItem.toString())
                    forma.parcela2 = parseInt(spinnerP2.selectedItem.toString())
                    forma.parcela3 = parseInt(spinnerP3.selectedItem.toString())
                    forma.parcela4 = parseInt(spinnerP4.selectedItem.toString())
                    forma.parcela5 = parseInt(spinnerP5.selectedItem.toString())
                    forma.parcela6 = parseInt(spinnerP6.selectedItem.toString())

                    taskAtualizar(forma)
                }else{
                    Toast.makeText(this,"Erro ao Cadastrar", Toast.LENGTH_SHORT).show()
                }
            }
            supportActionBar?.title = "Incluir Foma de Pagamento"
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

    private fun taskAtualizar(forma: FormaDePagamento) {
        // Thread para salvar a forma
        Thread {
            var resposta = FormaDePgtoService.save(forma)
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

    private fun taskPut(forma: FormaDePagamento) {
        //Thred para Atualizar a forma
        Thread {
            var resposta = FormaDePgtoService.edit(forma)
            runOnUiThread {
                if(resposta != null) {
                    Toast.makeText(this, "Error de conexão", Toast.LENGTH_SHORT).show()
                }else {
                    // após cadastrar, voltar para Tela de Cores
                    intent = Intent(this, TelaFormaDePgtoActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    //Validar os dados
    private fun validarDados() : Boolean {

        var v1 = 0; var v2 = 0
        var n = insertCodForma.text.toString()
        if (insertCodForma.text.toString() == ""){ n = "0"}

        if(!TextUtils.isEmpty(insertDescricao.text.toString()) && insertDescricao.text.length > 3 && insertDescricao.text.length < 31){
            v1 = 1
        }else{
            insertDescricao.setError("Descrição deve ter min 4 caracteres e max 30")
            insertDescricao.requestFocus()
        }

        if(parseInt(n) > 0 && parseInt(n) <= 999){
            v2 = 1
        }else{
            insertCodForma.setError("Codigo deve estar entre 1 e 999")
            insertCodForma.requestFocus()
        }
        if(v1 == 1 && v2 == 1){return true}
        return false
    }
}
