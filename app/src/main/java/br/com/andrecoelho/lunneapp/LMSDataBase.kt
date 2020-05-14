package br.com.andrecoelho.lunneapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Cores::class), version = 1)
abstract class LMSDataBase : RoomDatabase() {
    abstract fun coresDAO() : CoresDAO
}