package com.example.books_app.ui.view_model
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.books_app.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books_app.ApiInterface
import com.example.books_app.RetrofitClient
import com.example.books_app.model.BestPodcast
import com.example.books_app.model.BookImg
import com.example.books_app.model.BookStatus
import com.example.books_app.model.ChipData
import com.example.books_app.model.CuratedList
import com.example.books_app.model.CuratedListsResponse
import com.example.books_app.model.Episode
import com.example.books_app.model.GenreData
import com.example.books_app.model.GenrePodcast
import com.example.books_app.model.GenresResponse
import com.example.books_app.model.Podcast
import com.example.books_app.model.PodcastResponse
import com.example.books_app.model.TabData
import com.example.books_app.model.Topics
import com.example.books_app.ui.adapter.CustomBookAdapter
import com.example.books_app.ui.adapter.CustomBookListingAdapter
import com.example.books_app.ui.adapter.CustomBookTrendingAdapter
import com.example.books_app.ui.adapter.CustomChipAdapter
import com.example.books_app.ui.adapter.CustomExploreAdapter
import com.example.books_app.ui.adapter.CustomTabAdapter
import com.example.books_app.ui.adapter.CustomTopicAdapter
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _bookAdapter = MutableLiveData<CustomBookAdapter>()
    val bookAdapter: LiveData<CustomBookAdapter> get() = _bookAdapter

    private val _exploreBookAdapter = MutableLiveData<CustomExploreAdapter>()
    val exploreAdapter: LiveData<CustomExploreAdapter> get() = _exploreBookAdapter

    private val _listBookAdapter = MutableLiveData<CustomBookListingAdapter>()
    val listBookAdapter: LiveData<CustomBookListingAdapter> get() = _listBookAdapter

    private val _bookTrendingAdapter = MutableLiveData<CustomBookTrendingAdapter>()
    val bookTrendingAdapter: LiveData<CustomBookTrendingAdapter> get() = _bookTrendingAdapter

    private val _chipAdapter = MutableLiveData<CustomChipAdapter>()
    val chipAdapter: LiveData<CustomChipAdapter> get() = _chipAdapter

    private val _topicAdapter = MutableLiveData<CustomTopicAdapter>()
    val topicAdapter:LiveData<CustomTopicAdapter> get() = _topicAdapter

    private val _tabAdapter = MutableLiveData<CustomTabAdapter>()
    val tabAdapter:LiveData<CustomTabAdapter> get() = _tabAdapter

    private val _additionalGenresVisible = MutableLiveData<Boolean>()
    val additionalGenresVisible: LiveData<Boolean> get() = _additionalGenresVisible

    private val apiService = RetrofitClient.getInstance().create(ApiInterface::class.java)

//    private val _prodLiveData = MutableLiveData<List<ProductData>>()
//    private val productLiveData:LiveData<List<ProductData>> get() = _prodLiveData

    private val _genreLiveData = MutableLiveData<List<GenreData>>()
    private val genreLiveData:LiveData<List<GenreData>> get() = _genreLiveData

//    private val _genreNamesLiveData = MutableLiveData<List<String>>()
//    private val genreNamesLiveData:LiveData<List<String>> get() = _genreNamesLiveData

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _bookLiveData = MutableLiveData<List<CuratedList>>()
    private val bookLiveData:LiveData<List<CuratedList>> get() = _bookLiveData

    private val _podcastLiveData = MutableLiveData<List<Podcast>>()
    private val podcastLiveData:LiveData<List<Podcast>> get() = _podcastLiveData

    private val _myPodcastLiveData = MutableLiveData<List<Episode>>()
    private val myPodcastLiveData:LiveData<List<Episode>> get() = _myPodcastLiveData

    private val _bestPodcast = MutableLiveData<List<BestPodcast>>()
    private val bestPodcastLiveData:LiveData<List<BestPodcast>> get() = _bestPodcast

    private var genresList : List<ChipData> = listOf()
    private var podcastList: List<BookStatus> = listOf()
    private var allPodcasts2: List<Episode> = listOf()

    private val _selectedGenreInfo = MutableLiveData<Pair<Int, String>>()
    val selectedGenreInfo: LiveData<Pair<Int, String>> get() = _selectedGenreInfo

    init {
        getAllGenre()
        getPodcast()
        getBooks()
        getExploreBook()

        val tabItem = listOf(
            TabData(R.drawable.uil_fire,"Trending"),
            TabData(R.drawable.head_black,"Old")
        )
        _tabAdapter.value = CustomTabAdapter(tabItem)


        val topics = listOf(
            Topics("Personal Growth"),
            Topics("Culture & Society"),
            Topics("Fiction"),
            Topics("Mind & Philosophy"),
            Topics("Health & Fitness"),
            Topics("Biographies"),
            Topics("Education"),
            Topics("History"),
            Topics("Future"),
            Topics("Technology"),
            Topics("Life style")
        )
        _topicAdapter.value = CustomTopicAdapter(topics)
    }


    private fun getExploreBook(){
        viewModelScope.launch {
            try {
                val response:Response<CuratedListsResponse> = apiService.getBooksPodcast()
                if (response.isSuccessful){
                    Log.d("responseBook",response.body().toString())
                    _loadingVisibility.postValue(View.GONE)
                    val genreResponse:CuratedListsResponse? = response.body()
                    genreResponse?.let { it ->
                        val allPodcasts = mutableListOf<BookStatus>()
                        it.curated_lists.map { curatedData ->
                            allPodcasts.addAll(curatedData.podcasts.map {
                                Log.d("my_podcast",it.title.toString())
                                BookStatus(it.title,it.image)
                            })
                        }
                        _exploreBookAdapter.postValue(CustomExploreAdapter(allPodcasts))
                        _listBookAdapter.postValue(CustomBookListingAdapter(allPodcasts))
                        Log.d("CuratedList",podcastList.toString())
                    }
                }
            }
            catch (e:Exception){
                _loadingVisibility.postValue(View.GONE)
                Log.d("Exception: ", e.toString())
            }
        }
    }

    private fun getBooks(){
        _loadingVisibility.postValue(View.VISIBLE)
        viewModelScope.launch {
            try {
                val response:Response<CuratedListsResponse> = apiService.getBooksPodcast()
                if (response.isSuccessful){
                    Log.d("responseBook",response.body().toString())
                    _loadingVisibility.postValue(View.GONE)
                    val genreResponse:CuratedListsResponse? = response.body()
                    genreResponse?.let { it ->
                        val allPodcasts = mutableListOf<BookStatus>()
                        it.curated_lists.map { curatedData ->
                            allPodcasts.addAll(curatedData.podcasts.map {
                                 Log.d("my_podcast",it.title.toString())
                                 BookStatus(it.title,it.image)
                            })
                        }
                        _bookAdapter.postValue(CustomBookAdapter(allPodcasts))
                        Log.d("CuratedList",podcastList.toString())
                        //_bookLiveData.postValue(it.curated_lists)
                    }
                }
            }
            catch (e:Exception){
                _loadingVisibility.postValue(View.GONE)
                Log.d("Exception: ", e.toString())
            }
        }
    }

    private fun getPodcast(){
        _loadingVisibility.postValue(View.VISIBLE)
        viewModelScope.launch {
            try {
                val response:Response<PodcastResponse> = apiService.getPodcast()
                if (response.isSuccessful){
                    Log.d("responseBook",response.body().toString())
                    _loadingVisibility.postValue(View.GONE)
                    val podcastResponse:PodcastResponse? = response.body()
                    podcastResponse?.let { it ->
                        _myPodcastLiveData.postValue(it.episodes)
                        allPodcasts2 = it.episodes.map { myPodcast ->
                            Episode(
                                myPodcast.id,
                                myPodcast.link,
                                myPodcast.audio,
                                myPodcast.image,
                                myPodcast.title,
                                myPodcast.thumbnail,
                                myPodcast.description,
                                myPodcast.pubDateMs,
                                myPodcast.guidFromRss,
                                myPodcast.listennotesUrl,
                                myPodcast.audioLengthSec,
                                myPodcast.explicitContent,
                                myPodcast.maybeAudioInvalid,
                                myPodcast.listennotesEditUrl
                            )
                        }
                        _bookTrendingAdapter.postValue(CustomBookTrendingAdapter(allPodcasts2))

                        //_bookLiveData.postValue(it.curated_lists)
                    }
                }
            }
            catch (e:Exception){
                _loadingVisibility.postValue(View.GONE)
                Log.d("Exception: ", e.toString())
            }
        }
    }


//    private fun getPodcastByGenre(){
//        _loadingVisibility.postValue(View.VISIBLE)
//        viewModelScope.launch {
//            try {
//                val response:Response<BestPodcast> = apiService.getPodcastByGenre()
//                if (response.isSuccessful){
//                    Log.d("responseBook",response.body().toString())
//                    _loadingVisibility.postValue(View.GONE)
//                    val genreResponse:BestPodcast? = response.body()
//
//                    genreResponse?.let { it ->
//                        allPodcasts2 = it.podcasts.map { bestPodcast ->
//                            BookImg(bestPodcast.image,bestPodcast.title)
//                        }
//                        _bookTrendingAdapter.postValue(CustomBookTrendingAdapter(allPodcasts2))
//
//                        //_bookLiveData.postValue(it.curated_lists)
//                    }
//                }
//            }
//            catch (e:Exception){
//                _loadingVisibility.postValue(View.GONE)
//                Log.d("Exception: ", e.toString())
//            }
//        }
//    }

    private fun getAllGenre(){
        _loadingVisibility.postValue(View.VISIBLE)
        viewModelScope.launch {
            try {
                val response: Response<GenresResponse> = apiService.getAllGenre()
                if (response.isSuccessful){
                    Log.d("GenreResponse",response.body().toString())
                    val genreResponse:GenresResponse? = response.body()
                    genreResponse?.let {
                        _genreLiveData.postValue(it.genres)
                        genresList = it.genres.map { genre ->
                            ChipData(genre.name)
                        }
                        _chipAdapter.postValue(CustomChipAdapter(genresList))
                        Log.d("GenreName",genresList.toString())
//                        it.genreNames?.let {
//                            genreNames -> _genreNamesLiveData.postValue(genreNames)
//                        }
                       // Log.d("GenreName",it.genreNames.toString())
                        Log.d("BookApp: ",it.genres.toString())
                    }
                    _loadingVisibility.postValue(View.GONE)
                }
                else
                {
                    _loadingVisibility.postValue(View.GONE)
                    Log.d("BookApp: ",response.errorBody().toString())
                }
            }
            catch (e:Exception) {
                _loadingVisibility.postValue(View.GONE)
                Log.d("Exception: ", e.toString())
            }

        }
    }

//    private fun getAllProducts(){
//        viewModelScope.launch {
//            try {
//                val response: Response<ProductResponse> = apiService.getAllProducts()
//                if (response.isSuccessful){
//                    val prodResponse:ProductResponse? = response.body()
//                    prodResponse?.let {
//                        _prodLiveData.postValue(it.products)
//                        Log.d("ProductApp: ",it.products.toString())
//                    }
//                }
//                else
//                {
//                    Log.d("ProductApp: ",response.errorBody().toString())
//                }
//            }
//            catch (e:Exception){
//                Log.d("Exception: ",e.toString())
//            }
//        }
//    }
//    fun observeProducts(owner: LifecycleOwner, observer: Observer<List<ProductData>>){
//        productLiveData.observe(owner,observer)
//    }

    fun observeGenre(owner: LifecycleOwner, observer: Observer<List<GenreData>>){
        genreLiveData.observe(owner,observer)
    }

//    fun observePodcast(owner: LifecycleOwner, observer: Observer<List<Podcast>>){
//        podcastLiveData.observe(owner,observer)
//    }

    fun observeBook(owner: LifecycleOwner,observer: Observer<List<CuratedList>>){
        bookLiveData.observe(owner,observer)
    }

    fun observeEpisodes(owner: LifecycleOwner,observer: Observer<List<Episode>>){
        myPodcastLiveData.observe(owner,observer)
    }

//    fun observeExploredBook(owner: LifecycleOwner,observer: Observer<List<CuratedList>>){
//        exploreAdapter.observe(owner,observer)
//    }

    fun observeBestPodcast(owner: LifecycleOwner,observer: Observer<List<BestPodcast>>){
        bestPodcastLiveData.observe(owner,observer)
    }



//    fun observeGenreNames(owner: LifecycleOwner,observer: Observer<List<String>>){
//        genreNamesLiveData.observe(owner,observer)
//    }
}

