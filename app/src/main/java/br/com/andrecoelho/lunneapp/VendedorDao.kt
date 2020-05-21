package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VendedorDao {

    @Query("SELECT * FROM vendedor where id = :id")
    fun getById(id : Long) : Vendedor?

    @Query("SELECT * FROM vendedor")
    fun findAll() : List<Vendedor>

    @Insert
    fun insert(vendedor: Vendedor)

    @Delete
    fun delete(vendedor: Vendedor)
}