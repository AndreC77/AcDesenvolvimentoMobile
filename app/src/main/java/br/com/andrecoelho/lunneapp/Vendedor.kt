package br.com.andrecoelho.lunneapp

import com.google.gson.GsonBuilder
import java.io.Serializable

class Vendedor : Serializable {

    var id : Long = 0
    var codVendedor : Long = 0
    var nome : String = ""
    var emailVendedor : String = ""
    var telefone : String = ""
    var celular : String = ""
    var funcionario : String = ""
    var senha : String = ""

    override fun toString(): String {
        return "Vendedor: $nome"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}