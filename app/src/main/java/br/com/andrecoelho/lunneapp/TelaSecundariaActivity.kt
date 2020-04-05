package br.com.andrecoelho.lunneapp


import android.os.Bundle
import android.view.MenuItem


import androidx.appcompat.app.AppCompatActivity

class TelaSecundariaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_secundaria)
        val args = intent.extras
        val titulo = args?.getString("selecionado")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = titulo
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}