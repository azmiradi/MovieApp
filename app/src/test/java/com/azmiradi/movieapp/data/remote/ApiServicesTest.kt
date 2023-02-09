package com.azmiradi.movieapp.data.remote
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServicesTest {

    private lateinit var service: APIServices
    private lateinit var server: MockWebServer
    private val country: String = "eg"
    private var category: String = "general"
    private var page: Int = 1
    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIServices::class.java)
    }

    private fun enqueueMockResponse(){
        val inputStream = javaClass.classLoader!!.getResourceAsStream("Movie.json")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getMovieTest(){
        runBlocking {
            enqueueMockResponse()
            val responseBody = service.getTopRated().body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/3/movie/top_rated?api_key=97a05525f2eef8229868dfe763c62565")
        }
    }

    @Test
    fun getMovieListSizeTest(){
        runBlocking {
            enqueueMockResponse()
            val responseBody = service.getTopRated().body()
            val articlesList = responseBody!!.results
            assertThat(articlesList!!.size).isEqualTo(20)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}