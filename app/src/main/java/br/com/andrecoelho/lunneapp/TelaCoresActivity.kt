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
import kotlinx.android.synthetic.main.activity_tela_cores.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaCoresActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this
    private var cores = listOf<Cores>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cores)

        val args = intent.extras
        val titulo = args?.getString("selecionado")


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = titulo
        configuraMenuLateral()

        recyclerCores?.layoutManager = LinearLayoutManager(context)
        recyclerCores?.itemAnimator = DefaultItemAnimator()
        recyclerCores?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        taskCores()
    }

    fun taskCores(){

        Thread {
            this.cores = CoresService.getCores(context)
            runOnUiThread {
                recyclerCores?.adapter = CoresAdapter(cores) { onClikCores(it) }
            }
        }.start()
    }

    fun onClikCores(cores: Cores){
        Toast.makeText(context, "Clicou em ${cores.descricaoCor}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, CorActivity::class.java)
        intent.putExtra("cores",cores)
        startActivityForResult(intent, REQUEST_REMOVE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(context, TelaCadastroActivity::class.java)
        val id = item?.itemId
        if(id == R.id.action_adicionar){
            startActivityForResult(intent, REQUEST_CADASTRO)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraMenuLateral(){
        var toogle = ActionBarDrawerToggle(
            this,
            telaMenuLateral3,
            toolbar,
            R.string.nav_open,
            R.string.nav_close)
        telaMenuLateral3.addDrawerListener(toogle)
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
        telaMenuLateral3.closeDrawer(GravityCompat.START)

        return true
    }

    // esperar o retorno do cadastro da Cor
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE ) {
            // atualizar lista de disciplinas
            taskCores()
        }
    }
}
