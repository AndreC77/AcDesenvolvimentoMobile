package br.com.andrecoelho.lunneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        imageViewPrincipal.setImageResource(R.drawable.logo)

        buttonLogin.setOnClickListener {
            var intent = Intent(this, Tela_inicial_Activity ::class.java)
            val nomeUsuario = usuario.text.toString()
            val senhaUsuario = senha.text.toString()

            if(nomeUsuario == "aluno" && senhaUsuario == "impacta"){
                Toast.makeText(this, "Bem vindo Usuario: $nomeUsuario", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else {
                Toast.makeText(this, "Usuario ou Senha Incorreto", Toast.LENGTH_SHORT).show()
            }


        }


    }
}
