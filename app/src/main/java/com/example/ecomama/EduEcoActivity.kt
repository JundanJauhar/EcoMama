package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil

class EduEcoActivity : AppCompatActivity() {

    data class Article(
        val id: Int,
        val title: String,
        val description: String,
        val authorName: String,
        val publishDate: String,
        val readTime: String,

        )

    class ArticleAdapter(
        private val articles: MutableList<Article>,
        private val onItemClick: (Article) -> Unit
    ) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

        inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.tvArticleTitle)
            private val descriptionTextView: TextView = itemView.findViewById(R.id.tvArticleDescription)
            private val authorNameTextView: TextView = itemView.findViewById(R.id.tvAuthorName)
            private val publishDateTextView: TextView = itemView.findViewById(R.id.tvPublishDate)
            private val readTimeTextView: TextView = itemView.findViewById(R.id.tvReadTime)


            fun bind(article: Article) {
                titleTextView.text = article.title
                descriptionTextView.text = article.description
                authorNameTextView.text = article.authorName
                publishDateTextView.text = article.publishDate
                readTimeTextView.text = article.readTime


                itemView.setOnClickListener { onItemClick(article) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_item_artikel, parent, false)
            return ArticleViewHolder(view)
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            holder.bind(articles[position])
        }

        override fun getItemCount() = articles.size

        // Fungsi filter untuk pencarian
        fun filter(query: String): List<Article> {
            return articles.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }

        }

        fun updateList() {

        }
    }
    // DiffUtil Callback untuk perbandingan list
    class ArticleDiffCallback(
        private val oldList: List<Article>,
        private val newList: List<Article>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    private lateinit var recyclerViewArticles: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var btnBack: ImageButton
    private lateinit var articleAdapter: ArticleAdapter

    private val articleList = listOf(
        Article(
            id = 1,
            title = "Perjanjian Global untuk Akhiri Polusi Plastik",
            description = "Perjanjian internasional bertujuan untuk mengurangi polusi plastik melalui pengaturan siklus hidup plastik secara berkelanjutan.",
            authorName = "Green Network Asia",
            publishDate = "10 Desember 2023",
            readTime = "6 min read",

            ),
        Article(
            id = 2,
            title = "Kemajuan Kendaraan Listrik di Indonesia",
            description = "Adopsi kendaraan listrik meningkat 237% di Indonesia, mendukung transisi ke ekonomi hijau.",
            authorName = "Green Revolution",
            publishDate = "12 Desember 2023",
            readTime = "5 min read",

            ),
        Article(
            id = 3,
            title = "Energi Surya Terapung di Cirata",
            description = "Proyek energi surya terapung terbesar di Asia Tenggara diluncurkan di Cirata, Jawa Barat.",
            authorName = "Renewable Energy Insights",
            publishDate = "20 November 2023",
            readTime = "7 min read",

            ),
        Article(
            id = 4,
            title = "Urgensi Aksi Perubahan Iklim",
            description = "Laporan IPCC menunjukkan bahwa dunia harus mengurangi emisi gas rumah kaca sebesar 43% pada 2030 untuk mencegah dampak perubahan iklim yang parah.",
            authorName = "Climate Watch",
            publishDate = "15 Desember 2023",
            readTime = "8 min read",

            ),
        Article(
            id = 5,
            title = "Teknologi Hijau untuk Masa Depan",
            description = "Pertumbuhan teknologi hijau di Asia-Pasifik mendukung transisi menuju kehidupan berkelanjutan.",
            authorName = "Eco Tech",
            publishDate = "18 Desember 2023",
            readTime = "6 min read",

            ),
        Article(
            id = 6,
            title = "Hari Lingkungan Hidup 2024: Degradasi Lahan Membuat Kehidupan Masyarakat Dunia Terancam",
            description = "Degradasi lahan yang terus berlangsung di Indonesia mengancam keanekaragaman hayati dan kehidupan masyarakat.",
            authorName = "Sumber Berita Lingkungan",
            publishDate = "6 Januari 2024",
            readTime = "7 min read",

            ),
        Article(
            id = 7,
            title = "Lingkungan Hidup di Indonesia Makin Mengkhawatirkan",
            description = "Situasi lingkungan semakin memburuk dengan penambahan tekanan akibat deforestasi dan proyek industri.",
            authorName = "Lingkungan Indonesia",
            publishDate = "7 Januari 2024",
            readTime = "6 min read",

            ),
        Article(
            id = 8,
            title = "Melawan Perubahan Iklim dengan Energi Terbarukan",
            description = "Indonesia memperkuat komitmennya untuk mengurangi emisi karbon dengan mempercepat adopsi energi terbarukan.",
            authorName = "Sustainable Energy Solutions",
            publishDate = "10 Januari 2024",
            readTime = "5 min read",

            ),
        Article(
            id = 9,
            title = "Krisis Air di Asia Tenggara: Dampak Perubahan Iklim",
            description = "Perubahan iklim mengancam ketahanan air di kawasan Asia Tenggara, dengan banyak negara mengalami kekeringan ekstrim.",
            authorName = "Water for Life",
            publishDate = "13 Januari 2024",
            readTime = "6 min read",
        ),
        Article(
            id = 10,
            title = "Pembangunan Hijau di Kota-kota Besar Indonesia",
            description = "Kota-kota besar di Indonesia mulai menerapkan kebijakan pembangunan hijau untuk mengurangi polusi dan meningkatkan kualitas hidup.",
            authorName = "Urban Green Development",
            publishDate = "16 Januari 2024",
            readTime = "5 min read",

            ),
        Article(
            id = 11,
            title = "Solusi Pengelolaan Sampah Plastik dengan Teknologi Daur Ulang Canggih",
            description = "Teknologi terbaru dalam pengelolaan sampah plastik semakin mendukung upaya daur ulang dan mengurangi polusi.",
            authorName = "Plastic Waste Solutions",
            publishDate = "18 Januari 2024",
            readTime = "6 min read",

            ),Article(
            id = 12,
            title = "Inovasi Energi Hidrogen di Asia",
            description = "Asia memimpin dalam pengembangan teknologi hidrogen untuk mendukung transisi energi bersih.",
            authorName = "Clean Energy Hub",
            publishDate = "20 Januari 2024",
            readTime = "7 min read",
        ),
        Article(
            id = 13,
            title = "Mengatasi Banjir dengan Infrastruktur Berkelanjutan",
            description = "Solusi infrastruktur ramah lingkungan membantu mitigasi risiko banjir di kota-kota besar.",
            authorName = "Green Infrastructure",
            publishDate = "22 Januari 2024",
            readTime = "6 min read",
        ),
        Article(
            id = 14,
            title = "Pengelolaan Hutan Berkelanjutan untuk Masa Depan",
            description = "Strategi pengelolaan hutan yang berfokus pada keberlanjutan mulai diterapkan di Indonesia.",
            authorName = "Forest Sustainability",
            publishDate = "24 Januari 2024",
            readTime = "5 min read",
        ),
        Article(
            id = 15,
            title = "Revolusi Pengelolaan Limbah Elektronik",
            description = "Pendekatan baru dalam daur ulang limbah elektronik menawarkan solusi terhadap masalah lingkungan global.",
            authorName = "Eco Electronics",
            publishDate = "26 Januari 2024",
            readTime = "6 min read",
        ),
        Article(
            id = 16,
            title = "Konservasi Laut untuk Menyelamatkan Ekosistem",
            description = "Upaya konservasi laut di Indonesia menunjukkan hasil positif dalam melindungi keanekaragaman hayati.",
            authorName = "Marine Conservation",
            publishDate = "28 Januari 2024",
            readTime = "5 min read",
        ),
        Article(
            id = 17,
            title = "Peluang Ekonomi dari Ekowisata",
            description = "Ekowisata di Indonesia memberikan dampak positif terhadap ekonomi lokal dan konservasi lingkungan.",
            authorName = "Eco Travel Insights",
            publishDate = "30 Januari 2024",
            readTime = "7 min read",
        ),
        Article(
            id = 18,
            title = "Urban Farming di Tengah Kota",
            description = "Praktik urban farming membantu ketahanan pangan di kota besar sambil mengurangi jejak karbon.",
            authorName = "City Agriculture",
            publishDate = "1 Februari 2024",
            readTime = "6 min read",
        ),
        Article(
            id = 19,
            title = "Energi Angin di Indonesia: Potensi dan Tantangan",
            description = "Eksplorasi potensi energi angin di Indonesia untuk mendukung diversifikasi energi terbarukan.",
            authorName = "Wind Power Indonesia",
            publishDate = "3 Februari 2024",
            readTime = "6 min read",
        ),
        Article(
            id = 20,
            title = "Startup Hijau Mengubah Masa Depan",
            description = "Startup berbasis teknologi hijau di Asia-Pasifik memimpin inovasi untuk keberlanjutan global.",
            authorName = "Green Tech Innovators",
            publishDate = "5 Februari 2024",
            readTime = "7 min read",
        ),
        Article(
            id = 21,
            title = "Kampanye Anti Mikroplastik",
            description = "Kesadaran akan dampak mikroplastik mendorong masyarakat untuk beralih ke produk ramah lingkungan.",
            authorName = "Plastic Free Solutions",
            publishDate = "7 Februari 2024",
            readTime = "5 min read",
        )
    )





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edu_eco)



        // Inisialisasi komponen
        recyclerViewArticles = findViewById(R.id.recyclerViewArticles)
        searchView = findViewById(R.id.searchArticles)
        btnBack = findViewById(R.id.btnBack)

        // Setup RecyclerView
        recyclerViewArticles.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter(articleList.toMutableList()) { article ->
            // Handler klik artikel (bisa membuka detail artikel)
            tampilkanDetailArtikel(article)
            val intent = Intent(this, DetailArtikel::class.java)
            intent.putExtra("ARTICLE_DATA", article)  // Mengirimkan artikel yang dipilih
            startActivity(intent)
        }
        recyclerViewArticles.adapter = articleAdapter

        // Setup SearchView
        setupSearchView()

        // Setup tombol kembali
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    // Fungsi untuk setup search
    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val filteredList = articleAdapter.filter(it)
                    // Update adapter dengan list yang difilter
                    (recyclerViewArticles.adapter as? ArticleAdapter)?.let { adapter ->
                        // Catatan: Untuk implementasi lengkap, Anda perlu membuat method
                        // updateList di adapter
                        adapter.updateList()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList = articleAdapter.filter(it)
                    (recyclerViewArticles.adapter as? ArticleAdapter)?.let { adapter ->
                        adapter.updateList()
                    }
                }
                return true
            }
        })

    }

    // Fungsi untuk menampilkan detail artikel
    private fun tampilkanDetailArtikel(article: Article) {
        // Implementasi dialog atau intent untuk detail artikel
        val builder = AlertDialog.Builder(this)
        builder.setTitle(article.title)
        builder.setMessage("""
            Penulis: ${article.authorName}
            Tanggal: ${article.publishDate}
            
            ${article.description}
        """.trimIndent())
        builder.setPositiveButton("Tutup") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}

private fun Intent.putExtra(s: String, article: EduEcoActivity.Article){

}
