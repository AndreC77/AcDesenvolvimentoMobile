package br.com.andrecoelho.lunneapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "vendedor")
class Vendedor : Serializable {

    @PrimaryKey
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