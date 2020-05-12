package br.com.andrecoelho.lunneapp

import android.app.Application

class MaisVendasApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: MaisVendasApplication? = null

        fun getInstance(): MaisVendasApplication {
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