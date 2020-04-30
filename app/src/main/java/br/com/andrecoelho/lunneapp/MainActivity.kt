package br.com.andrecoelho.lunneapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.login.*

class MainActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //imageViewPrincipal.setImageResource(R.drawable.logo)

        buttonLogin.setOnClickListener {

            var intent = Intent(this, TelaInicialActivity ::class.java)
            val nomeUsuario = usuario.text.toString()
            val senhaUsuario = senha.text.toString()
            progressBar.visibility = View.VISIBLE

            if(nomeUsuario == "aluno" && senhaUsuario == "impacta"){
                Toast.makeText(this, "Bem vindo Usuario: $nomeUsuario", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else {
                Toast.makeText(this, "Usuario ou Senha Incorreto", Toast.LENGTH_SHORT).show()
            }


        }


    }
}
