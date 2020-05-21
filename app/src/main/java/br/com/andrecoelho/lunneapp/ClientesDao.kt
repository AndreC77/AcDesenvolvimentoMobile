package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClientesDao {

    @Query("SELECT * FROM clientes where idCliente = :id")
    fun getById(id : Long) : Clientes?

    @Query("SELECT * FROM clientes")
    fun findAll() : List<Clientes>

    @Insert
    fun insert(cliente : Clientes)

    @Delete
    fun delete(cliente : Clientes)
}