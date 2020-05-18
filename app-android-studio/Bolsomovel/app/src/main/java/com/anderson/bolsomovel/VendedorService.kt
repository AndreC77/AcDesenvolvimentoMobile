package com.anderson.bolsomovel

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

object VendedorService {

    //val host = "http://192.168.0.103:8080"
    val host = "http://7ristam.pythonanywhere.com"
    val TAG = "BolsomovelApp"

    fun getVendedores(context: Context): List<Vendedor> {
       val url = "$host/vendedores"
       val json = URL(url).readText()

        Log.v(TAG, json )

        return parserJson<List<Vendedor>>(json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson<T>(json, type)
    }
}