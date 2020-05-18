package com.anderson.bolsomovel

import androidx.room.Room

object DataBaseManager {
    private var dbInstance: BolsomovelDataBase

    init {
        val appContext = BolsomovelApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext,
            BolsomovelDataBase::class.java,
            "bolsomovel.sqlite"
        ).build()
    }

    fun getProdutoDAO(): ProdutoDAO {
        return dbInstance.produtoDAO()
    }
}