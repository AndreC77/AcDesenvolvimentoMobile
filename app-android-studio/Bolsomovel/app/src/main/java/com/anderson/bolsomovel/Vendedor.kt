package com.anderson.bolsomovel

import com.google.gson.GsonBuilder
import java.io.Serializable

class Vendedor: Serializable {
    var codVendedor: Int = 0
    var nome: String = ""
    var emailVendedor: String = ""
    var telefone: String = ""
    var celular: String = ""
    var funcionario: String = ""
    var senha: String = ""

    override fun toString(): String {
        return "Vendedor(nome='$nome')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}