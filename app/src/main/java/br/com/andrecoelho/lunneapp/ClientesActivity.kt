package br.com.andrecoelho.lunneapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_clientes.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.Serializable
import java.util.jar.Manifest

class ClientesActivity : DebugActivity() {

    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE = 2
    private val context: Context get() = this
    var cliente: Clientes? = null

    //busca a permisson
    val permisson = arrayOf(android.Manifest.permission.SEND_SMS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        //Pedir permissão ao usuario pra mandar sms
        ActivityCompat.requestPermissions(this, permisson,1)

        //recuperar objeto de Cliente da intent
          cliente = intent.getSerializableExtra("cliente") as Clientes


        // atualizar dados da disciplina
        idCliente.text = cliente?.idCliente.toString()
        nomeCliente.text = cliente?.nomeCompleto
        registroFederal.text = cliente?.registroFederal
        razaoSocial.text = cliente?.razaoSocial
        telCliente.text = cliente?.numeroTelefone
        celCliente.text = cliente?.numeroCelular

        //envento de click
        enviarSms.setOnClickListener {

            var numero = cliente?.numeroCelular
            var msg = txtSms.text.toString()

            //a classe que envia msg
            var sms = SmsManager.getDefault()
            sms.sendTextMessage(numero,null, msg,null,null)

            //ação que de Toast pra informar o usuario do envio da msg e zerar o campo de texto
            Toast.makeText(this, "Menssagen enviada!", Toast.LENGTH_SHORT).show()
            txtSms.setText("")

        }


        //colocar toobar
        setSupportActionBar(toolbar)

        //alterar titulo da ActionBar
        supportActionBar?.title = cliente?.nomeCompleto
        //up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // método sobrescrito para inflar o menu na Actionbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main_cliente, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this, CadastroClienteActivity::class.java)
        val id = item?.itemId
        if (id == android.R.id.home){
            finish()
        }else if(id == R.id.action_remover){
           // alerta para confirmar a remeção
           // só remove se houver confirmação positiva
           AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage("Deseja excluir Cliente?")
               .setPositiveButton("Sim") {
                       dialog, which ->
                   dialog.dismiss()
                   taskExcluir()
               }.setNegativeButton("Não") {
                       dialog, which -> dialog.dismiss()
               }.create().show()
        }else if(id == R.id.action_atualizar){
           intent.putExtra("clientes",cliente)
           startActivityForResult(intent, REQUEST_REMOVE)
       }

        return super.onOptionsItemSelected(item)
   }

    private fun taskExcluir() {
        if (this.cliente != null && this.cliente is Clientes) {
            // Thread para remover o cliente
            Thread {
                ClientesService.delete(this.cliente as Clientes)
                runOnUiThread {
                    // após remover, voltar para activity anterior
                    finish()
                }
            }.start()
        }
    }
}
