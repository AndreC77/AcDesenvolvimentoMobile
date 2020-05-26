package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tela_cliente.*
import kotlinx.android.synthetic.main.activity_tela_cliente.menulateral
import kotlinx.android.synthetic.main.activity_tela_estoque.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaEstoqueActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this
    private var estoque = listOf<Estoque>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_estoque)

//        val args = intent.extras
//        val titulo = args?.getString("selecionado")


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Estoque"
        configuraMenuLateral()

        recyclerEstoque?.layoutManager = LinearLayoutManager(context)
        recyclerEstoque?.itemAnimator = DefaultItemAnimator()
        recyclerEstoque?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskEstoque()
    }

    fun taskEstoque(){
        Thread {
            this.estoque = EstoqueService.getEstoque(context)
            runOnUiThread {
                recyclerEstoque?.adapter = EstoqueAdapter(estoque) { onClikEstoque(it) }
            }
        }.start()
    }

    fun onClikEstoque(estoque: Estoque){
        Toast.makeText(context, "Clicou em Estoque ${estoque.idEstoque}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, EstoqueActivity::class.java)
        intent.putExtra("estoque",estoque)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(context, CadastroEstoqueActivity::class.java)
        val id = item?.itemId
        if(id == R.id.action_adicionar){
            startActivityForResult(intent, REQUEST_CADASTRO)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraMenuLateral(){
        var toogle = ActionBarDrawerToggle(
            this,
            telaMenuLateral5,
            toolbar,
            R.string.nav_open,
            R.string.nav_close)
        telaMenuLateral5.addDrawerListener(toogle)
        toogle.syncState()

        menulateral.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var intent = Intent(this, MainActivity::class.java)

        var intent1 = Intent(this, TelaProdutosActivity::class.java)
        var intent2 = Intent(this, TelaClienteActivity::class.java)
        var intent3 = Intent(this, TelaVendedorActivity::class.java)
        var intent4 = Intent(this, TelaFormaDePgtoActivity::class.java)
        var intent5 = Intent(this, TelaCoresActivity::class.java)

        when (item.itemId){
            R.id.nav_produtos -> (
                    startActivity(intent1)
                    //Toast.makeText(this, "Menssagens", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_clientes -> (
                    startActivity(intent2)
                    //Toast.makeText(this, "Pedidos", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_vendedores -> (
                    startActivity(intent3)
                    //Toast.makeText(this, "Configuração", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_formaDePgto -> (
                    startActivity(intent4)
                    //Toast.makeText(this, "Localização", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_cores -> (
                    startActivity(intent5)
                    //Toast.makeText(this, "Site", Toast.LENGTH_SHORT).show()
                    )
        }

        if (item.itemId == R.id.nav_sair){
            Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
        telaMenuLateral5.closeDrawer(GravityCompat.START)
        return true
    }
}
