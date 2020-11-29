package sanchez.sanchez.sergio.postsapp.network.services.comments

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
import sanchez.sanchez.sergio.postsapp.persistence.network.service.ICommentsService
import java.io.File
import java.net.InetAddress


/**
 * Test for Comments Network Service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner::class)
class CommentsNetworkServiceUTest {

    @Test
    fun test_network_001_can_get_all_comments_for_a_post(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/network/services/comments/test_network_001_can_get_all_comments_for_a_post.json"))
                })

            val postId = 1L
            val comments = commentsService.getCommentsForPost(postId)

            comments.forEach {
                assertThat(it.postID).isEqualTo(postId)
            }

        }
    }

    @Test
    fun test_network_002_all_comments_has_a_valid_id(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/network/services/comments/test_network_002_all_comments_has_a_valid_id.json"))
                })

            val postId = 1L
            val comments = commentsService.getCommentsForPost(postId)

            comments.forEach {
                assertThat(it.id).isGreaterThan(0)
            }

        }
    }

    @Test
    fun test_network_003_no_comment_has_a_empty_body(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/network/services/comments/test_network_003_no_comment_has_a_empty_body.json"))
                })

            val postId = 1L
            val comments = commentsService.getCommentsForPost(postId)

            comments.forEach {
                assertThat(it.body).isNotEmpty
            }
        }
    }

    /**
     * Private Methods
     */

    /**
     * Helper function which will load a JSON from
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
        lateinit var commentsService : ICommentsService

        @JvmStatic
        @BeforeClass
        @Throws
        fun setup() {
            // Initialize mock webserver
            mockServer = MockWebServer().also {
                // Start the local server
                it.start(InetAddress.getByName("127.0.0.1"), 63637)
            }
            commentsService = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create(Gson()))
                .baseUrl(mockServer.url("").toString())
                .client(OkHttpClient.Builder().build())
                .build()
                .create(ICommentsService::class.java)

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