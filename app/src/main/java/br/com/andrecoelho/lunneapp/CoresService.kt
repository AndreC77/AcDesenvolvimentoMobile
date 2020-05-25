package br.com.andrecoelho.lunneapp

import android.content.Context
import android.util.Log
import br.com.fernandosousa.lmsapp.HttpHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CoresService {

    val host = "http://192.168.100.8:8080"
    val TAG = "WS_LMSApp"

    fun getCores(context: Context): List<Cores> {
        var cores = ArrayList<Cores>()
        if(AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/cor"
            val json = HttpHelper.get(url)
            cores = parserJson(json)
            //salvar offline
            for (c in cores){
                saveOffiline(c)
            }
            return cores
        }else{
            val dao = DatabaseManager.getCoresDAO()
            val cores = dao.findAll()
            return cores
        }
    }

    fun delete(cores: Cores): Response {
        Log.d(TAG, cores.idCor.toString())
        if(AndroidUtils.isInternetDisponivel(MaisVendasApplication.getInstance().applicationContext)) {
            val url = "${host}/cor/${cores.idCor}"
            val json = HttpHelper.delete(url)
            return parserJson(json)
        }
        val dao = DatabaseManager.getCoresDAO()
        dao.delete(cores)
        return Response(status = "OK", msg = "Dados Salvos Localmente")
    }

    fun save(cor: Cores): Response {
        val json = HttpHelper.post("$host/cor", cor.toJson())
        return parserJson(json)
    }

    fun edit(cor: Cores): Response {
        Log.d(TAG, cor.toJson())
        val json = HttpHelper.put("$host/cor/${cor.idCor}", cor.toJson())
        return parserJson(json)
    }

    //Salvar Offline
    fun saveOffiline(cor: Cores) : Boolean {
        val dao = DatabaseManager.getCoresDAO()
        if (! existeCor(cor)){
            dao.insert(cor)
        }
        return true
    }

    //Verificar se Ja existe O Cor
    fun existeCor(cor: Cores) : Boolean {
        val dao = DatabaseManager.getCoresDAO()
        return dao.getById(cor.idCor) != null
    }


    inline fun <reified T> parserJson(json: String) : T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)

    }

}