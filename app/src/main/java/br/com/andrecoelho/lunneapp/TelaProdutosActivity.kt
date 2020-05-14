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
import kotlinx.android.synthetic.main.activity_tela_produtos.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaProdutosActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val context: Context get() = this
    private var produtos = listOf<Produtos>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_produtos)

        val args = intent.extras
        val titulo = args?.getString("selecionado")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = titulo
        configuraMenuLateral()

        recyclerProdutos?.layoutManager = LinearLayoutManager(context)
        recyclerProdutos?.itemAnimator = DefaultItemAnimator()
        recyclerProdutos?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskProdutos()
    }

    fun taskProdutos(){

        Thread {
            this.produtos = ProdutosService.getProdutos(context)
            runOnUiThread {
                recyclerProdutos?.adapter = ProdutosAdapter(produtos) { onClikProdutos(it) }
            }

        }.start()
    }


    fun onClikProdutos(produtos: Produtos){
        Toast.makeText(context, "Clicou em ${produtos.descricao}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ProdutosActivity::class.java)
        intent.putExtra("produto",produtos)
        startActivityForResult(intent, REQUEST_REMOVE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(context, CadastroProdutoActivity::class.java)
        val id = item?.itemId
        if(id == R.id.action_adicionar){
            startActivityForResult(intent, REQUEST_CADASTRO)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraMenuLateral(){
        var toogle = ActionBarDrawerToggle(
            this,
            telaMenuLateral2,
            toolbar,
            R.string.nav_open,
            R.string.nav_close)
        telaMenuLateral2.addDrawerListener(toogle)
        toogle.syncState()

        menulateral.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var intent = Intent(this, MainActivity::class.java)

        when (item.itemId){
            R.id.nav_menssagens -> (
                    Toast.makeText(this, "Menssagens", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_pedidos -> (
                    Toast.makeText(this, "Pedidos", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_configuracao -> (
                    Toast.makeText(this, "Configuração", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_localizacao -> (
                    Toast.makeText(this, "Localização", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_site -> (
                    Toast.makeText(this, "Site", Toast.LENGTH_SHORT).show()
                    )
        }

        if (item.itemId == R.id.nav_sair){
            Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
        telaMenuLateral2.closeDrawer(GravityCompat.START)

        return true
    }

}
