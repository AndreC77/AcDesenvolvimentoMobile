package br.com.andrecoelho.lunneapp

import android.os.Bundle
import android.view.MenuItem

class ConfigMainActivity :DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.config_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Configurações"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}