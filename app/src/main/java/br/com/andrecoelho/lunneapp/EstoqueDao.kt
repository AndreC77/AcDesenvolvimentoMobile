package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EstoqueDao {

    @Query("SELECT * FROM estoques where idEstoque = :id")
    fun getById(id : Long) : Estoque?

    @Query("SELECT * FROM estoques")
    fun findAll() : List<Estoque>

    @Insert
    fun insert(estoque: Estoque)

    @Delete
    fun delete(estoque: Estoque)
}