package br.com.fernandosousa.lmsapp

import android.util.Log
import okhttp3.*
import java.io.IOException

object HttpHelper {

    private val TAG = "HTTP_LMSApp"
    private val LOG_ON = true
    val JSON = MediaType.parse("application/json; charset=utf-8")

    var client = OkHttpClient()

    // GET
    fun get(url:String): String {
        Log.d(TAG, "HttpHelper.get: $url")
        val request = Request.Builder().url(url).get().build()
        return getJson(request)
    }

    // POST JSON
    fun post(url: String, json: String): String {
        Log.d(TAG, "HttpHelper.post: $url > $json")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder().url(url).post(body).build()
        return getJson(request)
    }

    fun put(url: String, json: String): String {
        Log.d(TAG, "HttpHelper.put: $url > $json")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder().url(url).put(body).build()
        return getJson(request)
    }

    /*
    // POST com parâmetros (form-urlencoded)
    fun postForm(url: String, params: Map<String, String>): String {
        Log.d("HttpHelper.postForm: $url > $params")
        // Adiciona os parâmetros chave=valor na request POST
        val builder = FormBody.Builder()
        for ((key, value) in params) {
            builder.add(key, value)
        }
        val body = builder.build()
        // Faz a request
        val request = Request.Builder().url(url).post(body).build()
        return getJson(request)
    }*/

    // DELETE
    fun delete(url: String): String {
        Log.d(TAG, "HttpHelper.delete: $url")
        val request = Request.Builder().url(url).delete().build()
        return getJson(request)
    }


    // Lê resposta em formato JSON
    private fun getJson(request: Request?): String {
        val response = client.newCall(request).execute()
        val body = response.body()
        if (body != null) {
            val json = body.string()
            Log.d(TAG, "  << : $json")
            return json
        }
        throw IOException("Erro na requisição")
    }
}