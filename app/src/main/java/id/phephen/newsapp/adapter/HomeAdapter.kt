package id.phephen.newsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import id.phephen.newsapp.databinding.ItemSourceBinding
import id.phephen.newsapp.response.Articles
import id.phephen.newsapp.response.Source
import id.phephen.newsapp.response.Sources
import id.phephen.newsapp.ui.MainActivity

/**
 * Created by phephen on 10,February,2021
 * https://github.com/fendysaputro
 */
class HomeAdapter (private var sources: List<Sources>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.bind(sources[position])
    }

    override fun getItemCount() = sources.size

    fun updateData(newList: List<Sources>) {
        sources = newList
        notifyDataSetChanged()
    }

    class HomeViewHolder(private val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sources) {
            binding.tvSource.text = item.name
            binding.tvDesc.text = item.desc

            itemView.setOnClickListener {

            }
        }
    }

}