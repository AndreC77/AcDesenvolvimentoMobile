package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object VendedorService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getVendedor(context: Context): List<Vendedor> {
        var vendedores = ArrayList<Vendedor>()
        val url = "${host}/vendedores"
        val json = HttpHelper.get(url)
        vendedores = parserJson(json)
        return vendedores
    }


    fun delete(vendedor: Vendedor): Response {
        try {
            val url = "${host}/vendedores/${vendedor.id}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }catch (e : Exception) {
            return Response(status = "FAIL", msg = "Falha ao deletar Vendedor")
        }
    }

    fun save(vendedor: Vendedor): Response? {
        try {
            val json = HttpHelper.post("${host}/vendedores", vendedor.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao salvar Vendedor")
        }
    }

    fun edit(vendedor: Vendedor): Response? {
        try {
            val json = HttpHelper.put("$host/vendedores/${vendedor.id}", vendedor.toJson())
            return parserJson(json)
        }catch (e : Exception){
            return Response(status = "FAIL", msg = "Falha ao editar Vendedor")
        }
    }

    //Salvar Offline
    fun saveOffline(vendedor: Vendedor) : Boolean {
        val dao = DatabaseManager.getVendedorDAO()
        if (! existeVendedor(vendedor)){
            dao.insert(vendedor)
        }
        return true
    }

    //Verificar se Ja existe O Vendedor
    fun existeVendedor(vendedor: Vendedor) : Boolean {
        val dao = DatabaseManager.getVendedorDAO()
        return dao.getById(vendedor.id) != null
    }

//    fun senha(vendedor: Vendedor): Vendedor? {
//        if (AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
//            val json = HttpHelper.post("${host}/vendedores/login", vendedor.toJson())
//            if (json == "vendedor não cadatrado" || json == "senha invalida"){
//                return null
//            }else {
//                return parserJson(json)
//            }
//        }else{
//            return null
//        }
//    }

    fun senha(vendedor: Vendedor): Vendedor? {
        try {
            if (AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
                val json = HttpHelper.post("${host}/vendedores/login", vendedor.toJson())
                if (json == "vendedor não cadatrado" || json == "senha invalida") {
                    return null
                } else {
                    return parserJson(json)
                }
            } else {
                return null
            }
        }catch (e :Exception){
            return null
        }
    }


    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}