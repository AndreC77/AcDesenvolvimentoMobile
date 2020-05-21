package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EnderecoDao {

    @Query("SELECT * FROM endereco where idEndereco = :id")
    fun getById(id : Long) : Endereco?

    @Query("SELECT * FROM endereco")
    fun findAll() : List<Endereco>

    @Insert
    fun insert(endereco: Endereco)

    @Delete
    fun delete(endereco: Endereco)

}