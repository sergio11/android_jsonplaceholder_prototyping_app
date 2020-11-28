package sanchez.sanchez.sergio.postsapp.network.posts

import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository
import sanchez.sanchez.sergio.postsapp.persistence.network.exception.NetworkNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.CommentNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.PostNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.UserNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts.IPostsNetworkRepository
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts.PostsNetworkRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.network.service.ICommentsService
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IPostsService
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IUsersService
import java.io.File
import java.net.InetAddress

/**
 * Test for Posts Network Repository
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner::class)
class PostsNetworkRepositoryUTest {

    @Test
    fun test_network_repository_001_post_not_exists(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 404 Not Found"
                })

            try {
                val postId = 32321321321L
                postsNetworkRepository.findById(postId)
            } catch (ex: Exception) {
                assertThat(ex).isInstanceOf(NetworkNoResultException::class.java)
            }

        }
    }

    /**
     * Private Methods
     */

    /**
     * Helper function which will loadByDate JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */
    private fun getJson(path: String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }


    companion object {

        @JvmStatic
        lateinit var mockServer : MockWebServer

        @JvmStatic
        lateinit var postsNetworkRepository : IPostsNetworkRepository

        @JvmStatic
        @BeforeClass
        @Throws
        fun setup() {
            // Initialize mock webserver
            mockServer = MockWebServer().also {
                // Start the local server
                it.start(InetAddress.getByName("127.0.0.1"), 63637)
            }

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create(Gson()))
                .baseUrl(mockServer.url("").toString())
                .client(OkHttpClient.Builder().build())
                .build()

            val characterService = retrofit.create(IPostsService::class.java)
            val commentsService = retrofit.create(ICommentsService::class.java)
            val usersService = retrofit.create(IUsersService::class.java)

            postsNetworkRepository = PostsNetworkRepositoryImpl(
                characterService,
                commentsService,
                usersService,
                PostNetworkMapper(
                    CommentNetworkMapper(),
                    UserNetworkMapper()
                )
            )

        }

        @JvmStatic
        @AfterClass
        @Throws
        fun tearDown() {
            // We're done with tests, shut it down
            mockServer.shutdown()
        }
    }
}