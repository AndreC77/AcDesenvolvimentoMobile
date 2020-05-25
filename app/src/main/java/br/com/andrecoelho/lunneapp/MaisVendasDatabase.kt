package br.com.andrecoelho.lunneapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(/*Clientes::class,*/ Cores::class, Endereco::class, Estoque::class, FormaDePagamento::class, Produtos::class, Vendedor::class), version = 1)
abstract class MaisVendasDatabase : RoomDatabase() {

    //abstract fun clientesDAO() : ClientesDao

    abstract fun coresDAO() : CoresDao

    abstract fun enderecoDAO() : EnderecoDao

    abstract fun estoqueDAO() : EstoqueDao

    abstract fun formaDePgtoDAO() : FormaDePgtoDao

    abstract fun  produtosDAO() : ProdutosDao

    abstract fun vendedorDAO() : VendedorDao

}