package br.com.andrecoelho.lunneapp

import androidx.room.Room

object DatabaseManager {

    //singleton
    private var dbInstance : MaisVendasDatabase
    init {
        val appContext = MaisVendasApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext,//contexto global
            MaisVendasDatabase::class.java,//referencia da classe Banco
            "MaisVendas.sqlite"// nome do arquivo do banco
        ).build()
    }

//    fun getClientesDAO() : ClientesDao{
//        return dbInstance.clientesDAO()
//    }

    fun getCoresDAO() : CoresDao{
        return dbInstance.coresDAO()
    }

    fun getEnderecoDAO() : EnderecoDao{
        return dbInstance.enderecoDAO()
    }

    fun getEstoqueDAO() : EstoqueDao{
        return dbInstance.estoqueDAO()
    }

    fun getFormaDePgtoDAO() : FormaDePgtoDao{
        return dbInstance.formaDePgtoDAO()
    }

    fun getProdutosDAO() : ProdutosDao{
        return dbInstance.produtosDAO()
    }

    fun getVendedorDAO() : VendedorDao{
        return dbInstance.vendedorDAO()
    }

}