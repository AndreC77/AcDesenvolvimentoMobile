package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CoresDAO {

    @Query("SELECT * FROM cores WHERE idCor = :id")
    fun getById(id : Long) : Cores?

    @Query("SELECT * FROM cores")
    fun findAll(): List<Cores>

    @Insert
    fun insert(cor : Cores)

    @Delete
    fun delete(cor : Cores)

}