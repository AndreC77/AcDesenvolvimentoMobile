package br.com.andrecoelho.lunneapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle


import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tela_secundaria.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaSecundariaActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this
    private var clientes = listOf<Clientes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_secundaria)

        val args = intent.extras
        val titulo = args?.getString("selecionado")


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = titulo
        configuraMenuLateral()

        recyclerClientes?.layoutManager = LinearLayoutManager(context)
        recyclerClientes?.itemAnimator = DefaultItemAnimator()
        recyclerClientes?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        taskClientes()
    }

    fun taskClientes(){
        clientes = ClientesService.getCliente(context)
        recyclerClientes?.adapter = ClientesAdapter(clientes) {onClikClientes(it)}
    }

    fun onClikClientes(cliente: Clientes){
        Toast.makeText(context, "Clicou em ${cliente.NomeCompleto}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraMenuLateral(){
        var toogle = ActionBarDrawerToggle(
            this,
            telaMenuLateral1,
            R.string.nav_open,
            R.string.nav_close)
        telaMenuLateral1.addDrawerListener(toogle)
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
        telaMenuLateral1.closeDrawer(GravityCompat.START)
        
        return true
    }

}