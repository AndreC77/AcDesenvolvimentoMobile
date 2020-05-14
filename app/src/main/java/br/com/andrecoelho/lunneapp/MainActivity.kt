package br.com.andrecoelho.lunneapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_estoque.*
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

            /*Prefs.setBoolean("lembrar", checkLembrar.isChecked)
            if(checkLembrar.isChecked){
                Prefs.setString("lembrarNome", textUsuario)
                Prefs.setString("lembrarSenha", textSenha)
            }else{
                Prefs.setString("lembrarNome", textView )
                Prefs.setString("lembrarSenha", textView)
            }*/
            progressBar.visibility = View.VISIBLE

            if(nomeUsuario == "aluno" && senhaUsuario == "impacta"){
                Toast.makeText(this, "Bem vindo Usuario: $nomeUsuario", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else {
                Toast.makeText(this, "Usuario ou Senha Incorreto", Toast.LENGTH_SHORT).show()
            }


        }

        //var lembrar = Prefs.getBoolean("lembrar")
        //var ususarioL = Prefs.getString("lembrarNome")
       //var senhaL = Prefs.getString("lembrarSenha")

        //usuario.setText(ususarioL)
        //senha.setText(senhaL)
        //checkLembrar.isChecked = lembrar


    }
}
