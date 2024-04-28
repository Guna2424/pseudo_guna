package guna.pseudo.mobile

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import guna.pseudo.mobile.Data.Article
import guna.pseudo.mobile.Presentation.RecyclerAdapter
import guna.pseudo.mobile.Domain.SendRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerview: RecyclerView
    lateinit var context: Context
    lateinit var articless: List<Article>
    private lateinit var articleAdapter: RecyclerAdapter
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        actionBar?.hide()
        supportActionBar?.hide()

        progressBar = findViewById(R.id.progressBar)
        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(this@MainActivity, emptyList())
        recyclerview.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onStart() {
        super.onStart()
        showLoader()

        GlobalScope.launch {
            val response = SendRequest.sendRequest("")
            Log.e(TAG, "onStart: $response" )

            withContext(Dispatchers.Main) {
                hideLoader()
                if (response != null) {
                    val gson = Gson()
                    val responseJson = JSONObject(response)
                    val articlesJsonArray = responseJson.getJSONArray("articles")

                    val articles = mutableListOf<Article>()

                    for (i in 0 until articlesJsonArray.length()) {
                        val articleJson = articlesJsonArray.getJSONObject(i)
                        val author = articleJson.getString("author")
                        val title = articleJson.getString("title")
                        val description = articleJson.getString("description")
                        val url = articleJson.getString("url")
                        val urlToImage = articleJson.getString("urlToImage")
                        val publishedAt = articleJson.getString("publishedAt")
                        val content = articleJson.getString("content")

                        val article = Article(author, title, description, url, urlToImage, publishedAt,content)
                        articles.add(article)
                    }
                    updateUi(articles)

                }
            }
        }

    }

    private fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    private fun updateUi(articles: List<Article>) {
        adapter.articles = articles
        adapter.notifyDataSetChanged()
    }

}
