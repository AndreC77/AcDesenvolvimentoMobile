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

    private var resposta : Vendedor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //imageViewPrincipal.setImageResource(R.drawable.logo)

        buttonLogin.setOnClickListener {

            var vendedor = Vendedor()


            var intent = Intent(this, TelaInicialActivity::class.java)
            val nomeUsuario = usuario.text.toString()
            val senhaUsuario = senha.text.toString()

            //enviar Parametros preciso para POST SENHA
            vendedor.emailVendedor = nomeUsuario
            vendedor.senha = senhaUsuario

            progressBar.visibility = View.VISIBLE

            //POST  de Senha
            Thread {
                 resposta = VendedorService.senha(vendedor)
            }.start()
            resposta?.toJson()

            var name = resposta?.nome.toString()

            //Vlidacao do Login
            if(resposta != null){
                Toast.makeText(this, "Bem vindo Usuario: $name ", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else {
                Toast.makeText(this, "Usuario ou Senha Incorreto", Toast.LENGTH_SHORT).show()
                }
            progressBar.visibility = View.INVISIBLE
            }
        }
    }

