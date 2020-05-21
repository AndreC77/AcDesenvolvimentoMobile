package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CoresDao {

    @Query("SELECT * FROM cores where idCor = :id")
    fun getById(id : Long) : Cores?

    @Query("SELECT * FROM cores")
    fun findAll() : List<Cores>

    @Insert
    fun insert(cores: Cores)

    @Delete
    fun delete(cores: Cores)
}