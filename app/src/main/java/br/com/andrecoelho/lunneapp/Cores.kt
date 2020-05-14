package br.com.andrecoelho.lunneapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "cores")
class Cores: Serializable {

    @PrimaryKey
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