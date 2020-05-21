package br.com.andrecoelho.lunneapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "formasDePgto")
class FormaDePagamento : Serializable {

    @PrimaryKey
    var idFormaDePgto : Long = 0

    var descricaoFormaDePgto : String = ""
    var codigoFormaDePgto : Long = 0
    var parcela1 : Int = 0
    var parcela2 : Int = 0
    var parcela3 : Int = 0
    var parcela4 : Int = 0
    var parcela5 : Int = 0
    var parcela6 : Int = 0

    override fun toString(): String {
        return "Forma de Pagamento: $descricaoFormaDePgto"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}

