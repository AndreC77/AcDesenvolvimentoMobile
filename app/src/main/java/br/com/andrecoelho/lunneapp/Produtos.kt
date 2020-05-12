package br.com.andrecoelho.lunneapp

import com.google.gson.GsonBuilder
import java.io.Serializable

class Produtos : Serializable{

    var idProduto : Long = 0
    var codEan : Long = 0
    var codProduto : Long = 0
    var ref :  String = ""
    var descricao : String = ""
    var descricaoReduzida : String = ""
    var cor : String = ""
    var gradeVenda : String = ""
    var descricaoUnidadeVend : String = ""
    var unidadeVenda : String = ""
    var origem : Int = 0
    var ncm : String = ""
    var custo : Float = 0f
    var preco : Float = 0f
    var estoque : Long = 0
    var altura : Float = 0f
    var largura : Float = 0f
    var comprimento : Float = 0f
    var peso : Float = 0f


    override fun toString(): String {
        return "Cliente: $descricao"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}