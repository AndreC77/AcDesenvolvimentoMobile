package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FormaDePgtoDao {

    @Query("SELECT * FROM formasDePgto where idFormaDePgto = :id")
    fun getById(id : Long) : FormaDePagamento?

    @Query("SELECT * FROM formasDePgto")
    fun findAll() : List<FormaDePagamento>

    @Insert
    fun insert(forma : FormaDePagamento)

    @Delete
    fun delete(forma: FormaDePagamento)
}