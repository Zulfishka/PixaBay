 package com.example.pixabay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabay.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


 abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
     RecyclerView.OnScrollListener() {

     override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
         super.onScrolled(recyclerView, dx, dy)
         val visibleItemCount: Int = layoutManager.childCount
         val totalItemCount: Int = layoutManager.itemCount
         val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
         if (!isLoading() && !isLastPage()) {
             if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                 && firstVisibleItemPosition >= 0
             ) {
                 loadMoreItems()
             }
         }
     }

     protected abstract fun loadMoreItems()
     abstract fun isLastPage(): Boolean
     abstract fun isLoading(): Boolean
 }

 class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val adapter = PixaAdapter ()
    var page = 1
     var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
    }

    private fun initClickers () {
        with(binding) {
            imageRecView.adapter = adapter
            imageRecView.addOnScrollListener(object :
                PaginationScrollListener(imageRecView.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    page++
                    searchImage()
                }

                override fun isLastPage() = false

                override fun isLoading() = isLoading
            })

            changePageBtn.setOnClickListener {
                page++
               searchImage()

            }
            searchBtn.setOnClickListener {
                adapter.arrayList.clear()
                searchImage()
            }
        }
    }
            private fun ActivityMainBinding.searchImage() {
                isLoading = true
               RetrofitService.api.searchImage(searchEd.text.toString(), page = page)
                    .enqueue(object : Callback<PixaModel>{
                    override fun onResponse(
                        call: Call<PixaModel>,
                        response: Response<PixaModel>
                    ) {
                        isLoading = false
                        if (response.isSuccessful){
                            var data = response.body()?.hits
                            adapter.addImages(data!!)
                        }
                    }

                    override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                        isLoading = false
                    }
                    })
        }
    }