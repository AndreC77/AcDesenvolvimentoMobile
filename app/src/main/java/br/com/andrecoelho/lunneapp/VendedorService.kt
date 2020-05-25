package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object VendedorService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getVendedor(context: Context): List<Vendedor> {
        var vendedores = ArrayList<Vendedor>()
        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "${host}/vendedores"
            val json = HttpHelper.get(url)
            vendedores = parserJson(json)
            //salvar offline
            for (v in vendedores){
                saveOffline(v)
            }
            return vendedores
        }else{
            val dao = DatabaseManager.getVendedorDAO()
            val vendedores = dao.findAll()
            return vendedores
        }
    }

    fun delete(vendedor: Vendedor): Response {
        Log.d(TAG, vendedor.id.toString())
        if (AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
            val url = "${host}/vendedores/${vendedor.id}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }else{
            val dao = DatabaseManager.getVendedorDAO()
            dao.delete(vendedor)
            return Response(status = "OK", msg = "Dados Salvos Localmente")
        }
    }

    fun save(vendedor: Vendedor): Response {
        val json = HttpHelper.post("${host}/vendedores", vendedor.toJson())
        return parserJson(json)
    }

    fun edit(vendedor: Vendedor): Response {
        Log.d(TAG, vendedor.toJson())
        val json = HttpHelper.put("$host/vendedores/${vendedor.id}", vendedor.toJson())
        return parserJson(json)
    }

    //Salvar Offline
    fun saveOffline(vendedor: Vendedor) : Boolean {
        val dao = DatabaseManager.getVendedorDAO()
        if (! existeVendedor(vendedor)){
            dao.insert(vendedor)
        }
        return true
    }

    //Verificar se Ja existe O Cliente
    fun existeVendedor(vendedor: Vendedor) : Boolean {
        val dao = DatabaseManager.getVendedorDAO()
        return dao.getById(vendedor.id) != null
    }

    fun senha(vendedor: Vendedor): Vendedor? {
        val json = HttpHelper.post("${host}/vendedores/login", vendedor.toJson())
        if (json == "vendedor não cadatrado" || json == "senha invalida"){
            return null
        }
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}