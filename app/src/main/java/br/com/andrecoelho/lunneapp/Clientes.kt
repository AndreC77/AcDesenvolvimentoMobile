package br.com.andrecoelho.lunneapp

import com.google.gson.GsonBuilder
import java.io.Serializable

class Clientes : Serializable{

    var idCliente: Long = 0
    var codCliente: Long = 0
    var registroFederal: String = ""
    var pessoaJuridica: Boolean = false
    var inscricaoEstadual: String = ""
    var nomeCompleto: String = ""
    var razaoSocial: String = ""
    var nomeFantazia: String = ""
    var bloqueado: Boolean = false
    var numeroTelefone: String = ""
    var numeroCelular: String = ""
    var email: String = ""
    var observacao: String = ""
    var endereco: String = ""

    override fun toString(): String {
        return "Cliente: $nomeCompleto"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}