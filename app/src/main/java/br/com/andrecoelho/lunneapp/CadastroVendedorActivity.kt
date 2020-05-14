package br.com.andrecoelho.lunneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_cadastro_forma.*
import kotlinx.android.synthetic.main.activity_cadastro_vendedor.*
import java.lang.Long.parseLong


class CadastroVendedorActivity : DebugActivity() {

    var par1 = arrayOf("Sim", "Não")
    var selecao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_vendedor)

        var vendedor = Vendedor()

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

        buttonSalvarVendedor.setOnClickListener {

            vendedor.nome = insertNomeVendedor.text.toString()
            vendedor.emailVendedor = insertEmailVendedor.text.toString()
            vendedor.senha = insertSenhaVendedor.text.toString()
            vendedor.telefone = insertTelVendedor.text.toString()
            vendedor.celular = insertCelVendedor.text.toString()
            vendedor.codVendedor = parseLong(insertCodVendedor.text.toString())
            vendedor.funcionario = spinnerV.selectedItem.toString()
            taskAtualizar(vendedor)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Incluir Vendedor"
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

}
