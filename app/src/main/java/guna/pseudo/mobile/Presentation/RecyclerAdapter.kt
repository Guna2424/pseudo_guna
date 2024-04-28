package guna.pseudo.mobile.Presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import guna.pseudo.mobile.Data.Article
import guna.pseudo.mobile.R

class RecyclerAdapter(private val contexts: Context,data: List<Article>) : RecyclerView.Adapter<RecyclerAdapter.ArticleViewHolder>() {
    var articles: List<Article> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_view, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val author: TextView = itemView.findViewById(R.id.author)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val publishedAt: TextView = itemView.findViewById(R.id.publishedAt)
        private val imageview: ImageView = itemView.findViewById(R.id.imageview)

        fun bind(article: Article) {
            title.text = article.title
            author.text = article.author
            description.text = article.description
            publishedAt.text = article.publishedAt

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(imageview)

            itemView.setOnClickListener{
                val url = article.url
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                contexts.startActivity(intent)
            }
        }


    }


}
