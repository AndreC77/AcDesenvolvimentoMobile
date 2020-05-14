package br.com.andrecoelho.lunneapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_inicial.*

class TelaInicialActivity : DebugActivity() {

    override  fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)
        bemVindo.text = "Bem vindo!"

        buttonClientes.setOnClickListener {
            var intent = Intent(this, TelaClienteActivity ::class.java)
            val button = buttonClientes.text.toString()
            val params = Bundle()

            params.putString("selecionado", button)
            intent.putExtras(params)

            startActivity(intent)
        }

        buttonProdutos.setOnClickListener {
            var intent = Intent(this, TelaProdutosActivity ::class.java)
            val button = buttonProdutos.text.toString()
            val params = Bundle()

            params.putString("selecionado", button)
            intent.putExtras(params)


            startActivity(intent)
        }

        buttonCores.setOnClickListener {
            var intent = Intent(this, TelaCoresActivity ::class.java)

            val button = buttonCores.text.toString()
            val params = Bundle()

            params.putString("selecionado", button)
            intent.putExtras(params)


            startActivity(intent)
        }

        buttonFormaDePagamento.setOnClickListener{

            var intent = Intent(this, TelaFormaDePgtoActivity ::class.java)

            val button = buttonFormaDePagamento.text.toString()
            val params = Bundle()

            params.putString("selecionado", button)
            intent.putExtras(params)


            startActivity(intent)

        }

        buttonEstoque.setOnClickListener {

            var intent = Intent(this, TelaEstoqueActivity ::class.java)

            val button = buttonEstoque.text.toString()
            val params = Bundle()

            params.putString("selecionado", button)
            intent.putExtras(params)

            startActivity(intent)

        }

        buttonVendedores.setOnClickListener {
            var intent = Intent(this, TelaVendedorActivity ::class.java)

            val button = buttonEstoque.text.toString()
            val params = Bundle()

            params.putString("selecionado", button)
            intent.putExtras(params)

            startActivity(intent)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent = Intent(this, ConfigMainActivity ::class.java)
        var intent2 = Intent(this, TelaCadastroActivity ::class.java)
        val id = item.itemId

        if(id == android.R.id.home){
            finish()
        }else if(id == R.id.action_config){
            Toast.makeText(this, "Configurações", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }else if(id == R.id.action_adicionar){
              Toast.makeText(this, "Incluir cliente", Toast.LENGTH_SHORT).show()
              startActivity(intent2)
        }
        return super.onOptionsItemSelected(item)
    }

}


