package br.com.andrecoelho.lunneapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.login.*
import java.util.*
import kotlin.reflect.typeOf

class MainActivity : DebugActivity() {

    private var resposta: Vendedor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        var lembrar = Prefs.getBoolean("lembrar")
        if (lembrar){
            var lembrarNome = Prefs.getString("lembrarNome")
            var lembrarSenha = Prefs.getString("lembrarSenha")
            usuario.setText(lembrarNome)
            senha.setText(lembrarSenha)
            checkBoxLogin.isChecked = lembrar
        }

        buttonLogin.setOnClickListener {onClick()}
    }

    fun onClick(){

        var vendedor = Vendedor()

        var intent = Intent(this, TelaClienteActivity::class.java)
        val nomeUsuario = usuario.text.toString()
        val senhaUsuario = senha.text.toString()

        progressBar.visibility = View.VISIBLE

        //enviar Parametros preciso para POST SENHA
        vendedor.emailVendedor = nomeUsuario
        vendedor.senha = senhaUsuario

        //armazenar valor do checkbox
        Prefs.setBoolean("lembrar", checkBoxLogin.isChecked)

        //Verificar se é para lembrar nome e senha
        if(checkBoxLogin.isChecked){
            Prefs.setString("lembrarNome", nomeUsuario)
            Prefs.setString("lembrarSenha", senhaUsuario)
        }else{
            Prefs.setString("lembrarNome", "")
            Prefs.setString("lembrarSenha", "")
        }


        //POST  de Senha
        Thread {
            resposta = VendedorService.senha(vendedor)
            Log.d("senha",resposta.toString())
        }.start()
        resposta?.toJson()
        Log.d("senha","teste ${resposta.toString()}")
        var name = resposta?.nome.toString()

        //Validacao do Login
        if(resposta != null){
            Toast.makeText(this, "Bem vindo Usuario: $name ", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }else {
            Toast.makeText(this, "Usuario ou Senha Incorreto", Toast.LENGTH_SHORT).show()
        }
        progressBar.visibility = View.INVISIBLE
    }
}
