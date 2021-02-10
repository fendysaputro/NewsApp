package id.phephen.newsapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.phephen.newsapp.databinding.ItemNewsBinding
import id.phephen.newsapp.response.Articles

/**
 * Created by phephen on 09,February,2021
 * https://github.com/fendysaputro
 */
class NewsAdapter (private var articles: List<Articles>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount() = articles.size

    fun updateData(newList: List<Articles>) {
        articles = newList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Articles) {
            binding.txtTitle.text = item.title
            binding.txtAuthor.text = item.author
            binding.txtPublishDate.text = item.publishedAt
            binding.txtDescription.text = item.description
            Glide.with(binding.image).load(item.urlToImage).into(binding.image)

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                it.context.startActivity(intent)
            }
        }
    }

}