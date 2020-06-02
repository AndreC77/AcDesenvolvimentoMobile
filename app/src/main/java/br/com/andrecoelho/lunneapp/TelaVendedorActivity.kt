package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tela_cliente.*
import kotlinx.android.synthetic.main.activity_tela_cliente.menulateral
import kotlinx.android.synthetic.main.activity_tela_vendedor.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception

class TelaVendedorActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this
    private var vendedor = listOf<Vendedor>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_vendedor)

//        val args = intent.extras
//        val titulo = args?.getString("selecionado")


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Vendedores"
        configuraMenuLateral()

        recyclerVendedor?.layoutManager = LinearLayoutManager(context)
        recyclerVendedor?.itemAnimator = DefaultItemAnimator()
        recyclerVendedor?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        taskVendedor()
    }

    fun taskVendedor(){
        Thread {
            try {
                this.vendedor = VendedorService.getVendedor(context)
                runOnUiThread {
                    recyclerVendedor?.adapter = VendedorAdapter(vendedor) { onClikVendedor(it) }
                }
            }catch (e : Exception){
                runOnUiThread {
                    Toast.makeText(context, "Erro de Conexão", Toast.LENGTH_SHORT).show()
                    allert()
                }
            }
        }.start()
    }

    fun onClikVendedor(vendedor: Vendedor) {
        Toast.makeText(context, "Clicou em ${vendedor.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, VendedorActivity::class.java)
        intent.putExtra("vendedor", vendedor)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(context, CadastroVendedorActivity::class.java)
        val id = item?.itemId
        if(id == R.id.action_adicionar){
            startActivityForResult(intent, REQUEST_CADASTRO)
        }else if (id == R.id.action_request){
            taskVendedor()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraMenuLateral(){
        var toogle = ActionBarDrawerToggle(
            this,
            telaMenuLateral6,
            toolbar,
            R.string.nav_open,
            R.string.nav_close)
        telaMenuLateral6.addDrawerListener(toogle)
        toogle.syncState()
        menulateral.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var intent = Intent(this, MainActivity::class.java)

        var intent1 = Intent(this, TelaProdutosActivity::class.java)
        var intent2 = Intent(this, TelaEstoqueActivity::class.java)
        var intent3 = Intent(this, TelaClienteActivity::class.java)
        var intent4 = Intent(this, TelaFormaDePgtoActivity::class.java)
        var intent5 = Intent(this, TelaCoresActivity::class.java)

        when (item.itemId){
            R.id.nav_produtos -> (
                    startActivity(intent1)
                    //Toast.makeText(this, "Menssagens", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_estoque -> (
                    startActivity(intent2)
                    //Toast.makeText(this, "Pedidos", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_clientes -> (
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
        telaMenuLateral6.closeDrawer(GravityCompat.START)
        return true
    }

    private fun allert(){
        AlertDialog.Builder(context).setTitle(R.string.app_name).setMessage("Erro de Conexão")
            .setPositiveButton("Ok") {
                    dialog, which ->
                dialog.dismiss()
                taskVendedor()
            }.create().show()
    }

}
