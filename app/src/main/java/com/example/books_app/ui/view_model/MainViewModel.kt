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
import com.example.books_app.model.BookStatus
import com.example.books_app.model.CuratedList
import com.example.books_app.model.CuratedListsResponse
import com.example.books_app.model.Episode
import com.example.books_app.model.GenreData
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

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _bookAdapter = MutableLiveData<CustomBookAdapter?>()
    val bookAdapter: MutableLiveData<CustomBookAdapter?> get() = _bookAdapter

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

    private val apiService = RetrofitClient.getInstance().create(ApiInterface::class.java)

    private val _genreLiveData = MutableLiveData<List<GenreData>>()
    private val genreLiveData:LiveData<List<GenreData>> get() = _genreLiveData

    private val _bookLiveData = MutableLiveData<List<CuratedList>>()
    private val bookLiveData:LiveData<List<CuratedList>> get() = _bookLiveData

    private val _podcastLiveData = MutableLiveData<List<Podcast>>()
    private val podcastLiveData:LiveData<List<Podcast>> get() = _podcastLiveData

    private val _myPodcastLiveData = MutableLiveData<List<Episode>>()
    private val myPodcastLiveData:LiveData<List<Episode>> get() = _myPodcastLiveData

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _additionalGenresVisible = MutableLiveData<Boolean>()
    val additionalGenresVisible: LiveData<Boolean> get() = _additionalGenresVisible

    private val _shimmerVisibility = MutableLiveData<Int>()
    val shimmerVisibility: LiveData<Int> get() = _shimmerVisibility

    private var genresList : List<GenreData> = listOf()
    private var podcastList: List<BookStatus> = listOf()
    private var allPodcasts2: List<Episode> = listOf()

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

    //for showing in explore
    private fun getExploreBook(){
        viewModelScope.launch {
            try {
                val response:Response<CuratedListsResponse> = apiService.getBooksPodcast()
                if (response.isSuccessful){
                    Log.d("responseBook",response.body().toString())
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
                Log.d("Exception: ", e.toString())
            }
        }
    }

    fun toggleAdditionalGenresVisibility() {
        _additionalGenresVisible.value = !_additionalGenresVisible.value!!
    }

    //for showing status
    private fun getBooks(){
        viewModelScope.launch {
            try {
                _shimmerVisibility.postValue(View.VISIBLE)
                val response:Response<CuratedListsResponse> = apiService.getBooksPodcast()
                if (response.isSuccessful){
                    Log.d("responseBook",response.body().toString())
                    val genreResponse:CuratedListsResponse? = response.body()
                    genreResponse?.let { it ->
                        val allPodcasts = mutableListOf<BookStatus>()
                        it.curated_lists.map { curatedData ->
                            allPodcasts.addAll(curatedData.podcasts.map {
                                 Log.d("my_podcast",it.title)
                                 BookStatus(it.title,it.image)
                            })
                        }
                        _bookAdapter.postValue(CustomBookAdapter(allPodcasts))
                        Log.d("CuratedList",podcastList.toString())
                    }
                }
            }
            catch (e:Exception){
                Log.d("Exception: ", e.toString())
            }
            finally {
                _shimmerVisibility.postValue(View.GONE)
            }
        }
    }

    //for home screen trending list
    private fun getPodcast(){
        viewModelScope.launch {
            try {
                val response:Response<PodcastResponse> = apiService.getPodcast()
                if (response.isSuccessful){
                    Log.d("responseBook",response.body().toString())
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
                    }
                }
            }
            catch (e:Exception){
                Log.d("Exception: ", e.toString())
            }
        }
    }

    //for genre list
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
                            GenreData(genre.id,genre.name,genre.parent_id)
                        }
                        _chipAdapter.postValue(CustomChipAdapter(genresList))
                        Log.d("GenreName",genresList.toString())
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

    fun observeGenre(owner: LifecycleOwner, observer: Observer<List<GenreData>>){
        genreLiveData.observe(owner,observer)
    }
    fun observeBook(owner: LifecycleOwner,observer: Observer<List<CuratedList>>){
        bookLiveData.observe(owner,observer)
    }

    fun observeEpisodes(owner: LifecycleOwner,observer: Observer<List<Episode>>){
        myPodcastLiveData.observe(owner,observer)
    }
    fun observeStatus(owner: LifecycleOwner,observer: Observer<Int>){
        shimmerVisibility.observe(owner,observer)
    }

//    fun observeLoadingVisibility(owner: LifecycleOwner, observer: Observer<Int>) {
//        loadingVisibility.observe(owner, observer)
//    }

}

