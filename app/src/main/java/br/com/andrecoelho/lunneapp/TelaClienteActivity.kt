package br.com.andrecoelho.lunneapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.component1


import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_tela_cliente.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaClienteActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this
    private var clientes = listOf<Clientes>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cliente)

//        val args = intent.extras
//        val titulo = args?.getString("selecionado")

//        var teste : MenuItem? = null
//        var seila = teste?.isChecked
//        seila = true



        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Clientes"
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
        Thread {
            try{
            this.clientes = ClientesService.getCliente(context)
            runOnUiThread {
                recyclerClientes?.adapter = ClientesAdapter(clientes) { onClikClientes(it) }
            }
                //enviarNotificacao(this.clientes.get(0))
            }catch (e : Exception){
                runOnUiThread {
                    //mostrar um alert De Falta de Conexão
                    Toast.makeText(context, "Erro de Conexão", Toast.LENGTH_SHORT).show()
                    allert()
                }
            }
        }.start()
    }

    fun onClikClientes(cliente: Clientes){
        Toast.makeText(context, "Clicou em ${cliente.nomeCompleto}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ClientesActivity::class.java)
        intent.putExtra("cliente", cliente)
        startActivityForResult(intent, REQUEST_REMOVE)

}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(context, CadastroClienteActivity::class.java)
        val id = item?.itemId
        if(id == R.id.action_adicionar){
            startActivityForResult(intent, REQUEST_CADASTRO)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraMenuLateral(){
        var toogle = ActionBarDrawerToggle(
            this,
            telaMenuLateral1,
            toolbar,
            R.string.nav_open,
            R.string.nav_close)
        telaMenuLateral1.addDrawerListener(toogle)
        toogle.syncState()

        menulateral.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var intent1 = Intent(this, TelaProdutosActivity::class.java)
        var intent2 = Intent(this, TelaEstoqueActivity::class.java)
        var intent3 = Intent(this, TelaVendedorActivity::class.java)
        var intent4 = Intent(this, TelaFormaDePgtoActivity::class.java)
        var intent5 = Intent(this, TelaCoresActivity::class.java)

        item.setCheckable(true)
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
        }

        if (item.itemId == R.id.nav_sair){
            var intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
        telaMenuLateral1.closeDrawer(GravityCompat.START)
        return true
    }

    fun enviarNotificacao(cliente: Clientes) {
        //Intent para abrir tela quando clicar na notificacao
        val intent = Intent(this, ClientesActivity::class.java)
        //parametros extras
        intent.putExtra("cliente", cliente)
        //disparar notificacao
        NotificationUtil.create(this, 0, intent, "Mais Vendas","Você tem uma Nova mudança no cliente ${cliente.nomeCompleto}")
    }

    private fun allert(){
        AlertDialog.Builder(context).setTitle(R.string.app_name).setMessage("Erro de Conexão")
            .setPositiveButton("Ok") {
                    dialog, which ->
                dialog.dismiss()
                taskClientes()
            }.create().show()
    }

}