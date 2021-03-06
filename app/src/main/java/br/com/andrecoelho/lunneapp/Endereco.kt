package br.com.andrecoelho.lunneapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "enderecoEntity")
class Endereco : Serializable {

    @PrimaryKey
    var idEndereco : Long = 0

    var cep : String = ""
    var logradouro : String = ""
    var complemento : String = ""
    var bairro : String = ""
    var localidade : String = ""
    var uf : String = ""
    var unidade :  String = ""
    var ibge : Long = 0
    var gia : Long = 0
    var numero : String = ""
    var complementoNumero : String = ""

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

}