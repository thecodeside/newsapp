package news.agoda.com.sample.newslist

import android.support.v4.widget.SwipeRefreshLayout
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import news.agoda.com.sample.NewsApplication
import news.agoda.com.sample.R
import news.agoda.com.sample.base.BaseLceFragment
import news.agoda.com.sample.model.NewsEntity

@FragmentWithArgs
class NewsListFragment : BaseLceFragment<SwipeRefreshLayout, List<NewsEntity>,
        NewsListPresenter.View, NewsListPresenter>() {
    companion object {
        const val TAG = "NewsListFragment"
    }

    private lateinit var newsListComponent: NewsListComponent

    private var adapter: NewsListAdapter = NewsListAdapter(context)

    override fun createViewState(): LceViewState<List<NewsEntity>, NewsListPresenter.View>
            = NewsListViewState()

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean): String
            = e?.localizedMessage ?: getString(R.string.unknown_error)


    override fun getData(): List<NewsEntity> = adapter.getData()

    override fun setData(data: List<NewsEntity>) {
        adapter.setData(data)
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadData(pullToRefresh)
    }

    override fun createPresenter(): NewsListPresenter = newsListComponent.presenter()

    override val layoutRes: Int
        get() = R.layout.fragment_news_list


    override fun injectDependencies() {
        super.injectDependencies()
        newsListComponent = DaggerNewsListComponent.builder()
                .baseComponent(NewsApplication.baseComponent)
                .build()
        newsListComponent.inject(this)
    }
}

