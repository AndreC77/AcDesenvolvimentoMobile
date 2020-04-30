package br.com.andrecoelho.lunneapp

import android.os.Bundle
import android.view.MenuItem

class TelaCadastroActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Incluir Cliente"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}