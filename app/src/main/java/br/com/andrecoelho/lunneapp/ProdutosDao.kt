package br.com.andrecoelho.lunneapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProdutosDao {

    @Query("SELECT * FROM produtos where idProduto = :id")
    fun getById(id : Long) : Produtos?

    @Query("SELECT * FROM produtos")
    fun findAll() : List<Produtos>

    @Insert
    fun insert(produtos: Produtos)

    @Delete
    fun delete(produtos: Produtos)
}