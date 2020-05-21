package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FormaDePgtoDao {

    @Query("SELECT * FROM formasDePgto where idFormaDePgto = :id")
    fun getById(id : Long) : FormaDePgtoDao?

    @Query("SELECT * FROM formasDePgto")
    fun findAll() : List<Clientes>

    @Insert
    fun insert(forma : FormaDePgtoDao)

    @Delete
    fun delete(forma: FormaDePgtoDao)
}