package com.anderson.bolsomovel

import com.google.gson.GsonBuilder
import java.io.Serializable

class Produto: Serializable {
    var id: Long = 0
    var nome: String = ""
    var foto: String = ""
    var qtd: String = ""

    override fun toString(): String {
        return "Produto(nome='$nome')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}