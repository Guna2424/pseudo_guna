package guna.pseudo.mobile.Data


data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

data class ArticleList(
    val articles: List<Article>
)
