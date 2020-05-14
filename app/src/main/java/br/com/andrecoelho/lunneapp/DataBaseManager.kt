package br.com.andrecoelho.lunneapp

import androidx.room.Room

object DataBaseManager {

    private var dbInstance: LMSDataBase

    init {
        val appContext = LMSApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext,
            LMSDataBase::class.java,
            "lms.sqlite"
        ).build()
    }

    fun getCoresDao() : CoresDAO{
        return dbInstance.coresDAO()
    }

}