package com.anderson.bolsomovel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProdutoDAO {
    @Query("SELECT * FROM produto WHERE id = :id")
    fun getById(id: Long): Produto?

    @Query("SELECT * FROM produto")
    fun findAll(): List<Produto>

    @Insert
    fun insert(produto: Produto)

    @Delete
    fun delete(produto: Produto)
}