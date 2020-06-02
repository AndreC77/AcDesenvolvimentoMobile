package br.com.andrecoelho.lunneapp

data class Response (val status: String, val msg: String) {
   fun isOK() = "OK".equals(status, ignoreCase = true)
   fun noOk() = "FAIL".equals(status,ignoreCase = true)
}