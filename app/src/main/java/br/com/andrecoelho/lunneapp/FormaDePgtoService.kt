package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FormaDePgtoService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getForma(context: Context): List<FormaDePagamento> {
        var formas = ArrayList<FormaDePagamento>()
        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "${host}/formadepagamento"
            val json = HttpHelper.get(url)
            formas = parserJson(json)
            //salvar offline
            for (f in formas){
                saveOffline(f)
            }
            return formas
        }else{
            val dao = DatabaseManager.getFormaDePgtoDAO()
            val formas = dao.findAll()
            return formas
        }
    }

    fun delete(forma: FormaDePagamento): Response {
        Log.d(TAG, forma.idFormaDePgto.toString())
        if (AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
            val url = "${host}/formadepagamento/${forma.idFormaDePgto}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        }else{
            val dao = DatabaseManager.getFormaDePgtoDAO()
            dao.delete(forma)
            return Response(status = "OK", msg = "Dados Salvos Localmente")
        }
    }

    fun save(forma: FormaDePagamento): Response {
        val json = HttpHelper.post("${host}/formadepagamento", forma.toJson())
        return parserJson(json)
    }

    fun edit(forma: FormaDePagamento): Response {
        Log.d(TAG, forma.toJson())
        val json = HttpHelper.put("$host/formadepagamento/${forma.idFormaDePgto}", forma.toJson())
        return parserJson(json)
    }

    //Salvar Offline
    fun saveOffline(forma: FormaDePagamento) : Boolean {
        val dao = DatabaseManager.getFormaDePgtoDAO()
        if (! existeForma(forma)){
            dao.insert(forma)
        }
        return true
    }

    //Verificar se Ja existe O Cliente
    fun existeForma(forma: FormaDePagamento) : Boolean {
        val dao = DatabaseManager.getFormaDePgtoDAO()
        return dao.getById(forma.idFormaDePgto) != null
    }

    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}