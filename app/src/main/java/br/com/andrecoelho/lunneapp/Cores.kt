package br.com.andrecoelho.lunneapp

import com.google.gson.GsonBuilder
import java.io.Serializable

class Cores: Serializable {

    var idCor : Long = 0
    var descricaoCor : String = ""
    var codCor : Long = 0

    override fun toString(): String {
        return "Cor: $descricaoCor"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}