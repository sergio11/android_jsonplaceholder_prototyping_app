package sanchez.sanchez.sergio.postsapp.repository

import android.os.Build
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoException
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.PostsRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.IPostDBRepository
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.PostDBRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.db.room.AppRoomDatabase
import sanchez.sanchez.sergio.postsapp.persistence.db.room.mapper.PostDBMapper
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


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class PostsRepositoryUTest {

    private lateinit var mockServer : MockWebServer
    private lateinit var database: AppRoomDatabase
    private lateinit var postsRepository: IPostsRepository
    private lateinit var postsNetworkRepository: IPostsNetworkRepository
    private lateinit var postsDBRepository: IPostDBRepository

    @Test
    fun test_repository_001_the_post_can_not_be_found(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 404 Not Found"
                })

            val postId = 34324L
            try {
                postsRepository.findById(postId)
            } catch (ex: RepoException) {

                verify(postsDBRepository, times(1)).findById(postId)
                verify(postsNetworkRepository, times(1)).findById(postId)
                verifyNoMoreInteractions(postsDBRepository)

                assertThat(ex).isInstanceOf(RepoNoResultException::class.java)
                assertThat(ex.cause).isInstanceOf(NetworkNoResultException::class.java)
            }


        }
    }

    @Test
    fun test_repository_002_post_retrieved_from_network_and_save_into_local_persistence(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/repository/posts/test_repository_002_post_retrieved_from_network_and_save_into_local_persistence_1.json"))
                })

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/repository/posts/test_repository_002_post_retrieved_from_network_and_save_into_local_persistence_2.json"))
                })

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/repository/posts/test_repository_002_post_retrieved_from_network_and_save_into_local_persistence_3.json"))
                })

            val postId = 1L

            val postDetail = postsRepository.findById(postId)

            verify(postsDBRepository, times(1)).findById(postId)
            verify(postsNetworkRepository, times(1)).findById(postId)

            // Verify that the post was saved into DB
            verify(postsDBRepository, times(1)).save(any())

            assertThat(postDetail.id).isEqualTo(postId)
            assertThat(postDetail.title).isNotEmpty
            assertThat(postDetail.body).isNotEmpty

        }
    }

    @Test
    fun test_repository_003_all_posts_are_retrieved_from_network() {
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/repository/posts/test_repository_003_all_posts_are_retrieved_from_network.json"))
                })


            postsRepository.findAllPosts()

            verify(postsNetworkRepository, times(1)).findAll()
            verifyNoMoreInteractions(postsNetworkRepository)
            // Only use the network layer for this operation
            verifyNoMoreInteractions(postsDBRepository)

        }
    }


    /**
     * Private Methods
     */

    /**
     * Helper function which will load JSON from
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


    /**
     * Setup
     */
    @Before
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

        val postsService = retrofit.create(IPostsService::class.java)
        val commentsService = retrofit.create(ICommentsService::class.java)
        val usersService = retrofit.create(IUsersService::class.java)

        // Create Spy for network repository
        postsNetworkRepository = spy(PostsNetworkRepositoryImpl(
            postsService,
            commentsService,
            usersService,
            PostNetworkMapper(
                CommentNetworkMapper(),
                UserNetworkMapper()
            )
        ))


        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppRoomDatabase::class.java).build()

        // Create Spy for db repository
        postsDBRepository = spy(PostDBRepositoryImpl(
            database.postDAO(),
            PostDBMapper()
        ))

        postsRepository = PostsRepositoryImpl(
            postsNetworkRepository, postsDBRepository
        )

    }

    /**
     * Tear Down
     */
    @After
    @Throws
    fun tearDown() {
        database.close()
        mockServer.shutdown()
    }
}