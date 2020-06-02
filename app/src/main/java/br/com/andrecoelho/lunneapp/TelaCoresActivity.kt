package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tela_cliente.menulateral
import kotlinx.android.synthetic.main.activity_tela_cores.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception
import android.net.ConnectivityManager
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class TelaCoresActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this
    private var cores : List<Cores>? = null
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cores)


//        val args = intent.extras
//        val titulo = args?.getString("selecionado")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cores"
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
            try {
                this.cores = CoresService.getCores(context)!!
                runOnUiThread {
                    recyclerCores?.adapter = CoresAdapter(cores!!) { onClikCores(it) }
                }
            }catch (e :  Exception){
                runOnUiThread {
                    //mostrar um alert De Falta de Conexão
                    Toast.makeText(context, "Erro de Conexão", Toast.LENGTH_SHORT).show()
                    allert()
                }
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
        }else if (id == R.id.action_request){
            taskCores()
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

        var intent1 = Intent(this, TelaProdutosActivity::class.java)
        var intent2 = Intent(this, TelaEstoqueActivity::class.java)
        var intent3 = Intent(this, TelaVendedorActivity::class.java)
        var intent4 = Intent(this, TelaFormaDePgtoActivity::class.java)
        var intent5 = Intent(this, TelaClienteActivity::class.java)


        when (item.itemId){
            R.id.nav_produtos -> (
                    startActivity(intent1)
                    //Toast.makeText(this, "Menssagens", Toast.LENGTH_SHORT).show()
                    )
            R.id.nav_estoque -> (
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
            R.id.nav_clientes -> (
                    startActivity(intent5)
                    //Toast.makeText(this, "Site", Toast.LENGTH_SHORT).show()
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

    private fun allert(){
        AlertDialog.Builder(context).setTitle(R.string.app_name).setMessage("Erro de Conexão")
            .setPositiveButton("Ok") {
                    dialog, which ->
                dialog.dismiss()
                taskCores()
            }.create().show()
    }
}
