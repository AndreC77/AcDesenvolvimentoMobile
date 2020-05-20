package com.anderson.bolsomovel

import android.app.Application

class BolsomovelApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: BolsomovelApplication? = null

        fun getInstance(): BolsomovelApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configurar application no Android Manifest")
            }
            return appInstance!!
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }

}