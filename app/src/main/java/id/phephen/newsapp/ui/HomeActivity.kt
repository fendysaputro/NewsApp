package id.phephen.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import id.phephen.newsapp.adapter.HomeAdapter
import id.phephen.newsapp.databinding.ActivityHomeBinding
import id.phephen.newsapp.helper.ResultWrapper
import id.phephen.newsapp.helper.isVisible
import id.phephen.newsapp.helper.nonEmptyStringOrNull
import id.phephen.newsapp.response.NewsResponse
import id.phephen.newsapp.response.Sources
import id.phephen.newsapp.response.SourcesResponse
import id.phephen.newsapp.viewmodel.DataViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvSource: RecyclerView
    private lateinit var tvInfo: TextView

    private lateinit var viewModel: DataViewModel
    private var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initialize()
        getDataSource()
    }

    private fun initView() {
        rvSource = binding.rvSource
        tvInfo = binding.tvInfo
    }

    private fun initialize() {
        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(DataViewModel::class.java)
    }

    private fun getDataSource() {
        viewModel.sourcesLiveData.observe(this, Observer { result ->
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

        viewModel.fetchSourcesData()
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            tvInfo.isVisible = true
            tvInfo.text = "Loading"
        } else {
            tvInfo.isVisible = false
        }
    }

    private fun onGetDataSuccess(data: SourcesResponse?) {
        if (data?.sources?.isNotEmpty() == true) {
            if (adapter == null) {
                adapter = HomeAdapter(data.sources)
                rvSource.adapter = adapter
                if (rvSource.itemDecorationCount == 0) {
                    rvSource.addItemDecoration(
                        DividerItemDecoration(
                            this,
                            LinearLayout.VERTICAL
                        )
                    )
                }
            } else {
                adapter?.updateData(data.sources)
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