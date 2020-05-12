package br.com.andrecoelho.lunneapp

import com.google.gson.GsonBuilder
import java.io.Serializable

class Estoque : Serializable {

    var idEstoque : Long = 0
    var codProduto : Long = 0
    var qtdEstoque : Long = 0
    var qtdVendida : Long = 0
    var qtdSaldo : Long = 0

    override fun toString(): String {
        return "Codigo: $codProduto , Quantidade Estoque: $qtdEstoque , Quantidade Vendida: $qtdVendida , Saldo: $qtdSaldo"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}