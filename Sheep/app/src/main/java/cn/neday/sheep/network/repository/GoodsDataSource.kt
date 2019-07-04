/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.neday.sheep.network.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.network.api.GoodsApi
import com.android.example.paging.pagingwithnetwork.reddit.api.RedditApi
import com.android.example.paging.pagingwithnetwork.reddit.repository.NetworkState
import com.android.example.paging.pagingwithnetwork.reddit.vo.RedditPost
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class GoodsDataSource(
    private val goodsApi: GoodsApi,
    private val subredditName: String,
) : PageKeyedDataSource<String, Pages<List<Goods>>>() {
    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null


    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Pages<List<Goods>>>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Pages<List<Goods>>>) {
        goodsApi.getNineOpGoodsList(
            subreddit = subredditName,
            after = params.key,
            limit = params.requestedLoadSize
        ).enqueue(
            object : retrofit2.Callback<RedditApi.ListingResponse> {
                override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                    // keep a lambda for future retry
                    retry = {
                        loadAfter(params, callback)
                    }
                    // publish the error
                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                }

                override fun onResponse(
                    call: Call<RedditApi.ListingResponse>,
                    response: Response<RedditApi.ListingResponse>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body()?.data?.children?.map { it.data } ?: emptyList()
                        // clear retry since last request succeeded
                        retry = null
                        callback.onResult(items)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                            NetworkState.error("error code: ${response.code()}")
                        )
                    }
                }
            }
        )
    }

    /**
     * The name field is a unique identifier for post items.
     * (no it is not the title of the post :) )
     * https://www.reddit.com/dev/api
     */
    override fun getKey(item: Pages<List<Goods>>): String = item.pageId

}