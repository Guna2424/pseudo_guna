package guna.pseudo.mobile.Domain

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object SendRequest {

    private const val _ip = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

    fun sendRequest(URL: String): String? {
        var conn: HttpURLConnection? = null
        val type = "application/json"
        val method = "GET"
        var response = "error: $method failed"
        val url = "$_ip$URL"
        println("printing URL==>>>>>>>>>>>>>>>>>$url")
        try {
            val u = URL(url)
            conn = u.openConnection() as HttpURLConnection
            conn.requestMethod = method
            conn.setRequestProperty("Content-Type", type)
            conn.setRequestProperty("Accept", type)
            conn.connectTimeout = 60000
            val sb = StringBuilder()
            BufferedReader(InputStreamReader(conn.inputStream)).use { input ->
                var line: String?
                while (input.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            }
            response = sb.toString()
            return response
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            conn?.disconnect()
        }
        return null
    }

}