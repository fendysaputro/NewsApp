package id.phephen.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.phephen.newsapp.databinding.ItemSourceBinding
import id.phephen.newsapp.response.Articles
import id.phephen.newsapp.response.Source

/**
 * Created by phephen on 10,February,2021
 * https://github.com/fendysaputro
 */
class HomeAdapter (private var sources: List<Articles>, private val listener: (Articles) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.bind(sources[position], listener)
    }

    override fun getItemCount() = sources.size

    inner class HomeViewHolder(private val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Articles, listener: (Articles) -> Unit) {
            binding.tvSource.text = item.source.name

            itemView.setOnClickListener {
                listener(item)
            }
        }
    }

}