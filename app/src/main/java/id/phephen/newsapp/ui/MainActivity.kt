package id.phephen.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.phephen.newsapp.adapter.NewsAdapter
import id.phephen.newsapp.databinding.ActivityMainBinding
import id.phephen.newsapp.helper.ResultWrapper
import id.phephen.newsapp.helper.isVisible
import id.phephen.newsapp.helper.nonEmptyStringOrNull
import id.phephen.newsapp.response.Articles
import id.phephen.newsapp.response.NewsResponse
import id.phephen.newsapp.viewmodel.DataViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvNews: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvInfo: TextView

    private lateinit var viewModel: DataViewModel
    private var adapter: NewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initialize()
        getDataNews()
    }

    private fun initView() {
        rvNews = binding.rvNews
        progressBar = binding.progressBar
        tvInfo = binding.tvInfo
    }

    private fun initialize() {
        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(DataViewModel::class.java)
    }

    private fun getDataNews() {
        viewModel.newsLiveData.observe(this, Observer { result ->
            isLoading(result.isLoading)

            when (result.state) {
                ResultWrapper.State.SUCCESS -> {
                    onGetDataSuccess(result.data)
                }

                ResultWrapper.State.FAILURE -> {
                    onGetDataFailed(result.error)
                }
            }
        })

        viewModel.fetchNewsData()
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            tvInfo.isVisible = true
            tvInfo.text = "Loading"
        } else {
            tvInfo.isVisible = false
        }
    }

    private fun onGetDataSuccess(data: NewsResponse?) {
        if (data?.articles?.isNotEmpty() == true) {
            if (adapter == null) {
                adapter = NewsAdapter(data.articles)
                rvNews.adapter = adapter
                if (rvNews.itemDecorationCount == 0) {
                    rvNews.addItemDecoration(
                            DividerItemDecoration(
                                    this,
                                    LinearLayout.VERTICAL
                            )
                    )
                }
            } else {
                adapter?.updateData(data.articles)
            }
        } else {
            tvInfo.isVisible = true
            tvInfo.text = "No Search Results found for"
        }
    }

    private fun onGetDataFailed(error: Throwable?) {
        val errorMessage = error?.message.nonEmptyStringOrNull() ?: "Something went wrong."
        tvInfo.text = errorMessage
        tvInfo.isVisible = true
    }
}