package sanchez.sanchez.sergio.postsapp.network.services.users

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
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IUsersService
import java.io.File
import java.net.InetAddress


/**
 * Test for Users Network Service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner::class)
class UsersNetworkServiceUTest {


    @Test
    fun test_network_001_can_get_user_detail_by_id(){
        runBlocking {

            mockServer.enqueue(
                MockResponse().apply {
                    status = "HTTP/1.1 200 OK"
                    setBody(getJson("json/network/services/users/test_network_001_can_get_user_detail_by_id.json"))
                })

            val userId = 1L
            val userDetail = usersService.getUserDetail(userId)

            assertThat(userDetail.id).isEqualTo(userId)
            assertThat(userDetail.name).isNotEmpty
            assertThat(userDetail.email).isNotEmpty
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


    companion object {

        @JvmStatic
        lateinit var mockServer : MockWebServer

        @JvmStatic
        lateinit var usersService : IUsersService

        @JvmStatic
        @BeforeClass
        @Throws
        fun setup() {
            // Initialize mock webserver
            mockServer = MockWebServer().also {
                // Start the local server
                it.start(InetAddress.getByName("127.0.0.1"), 63637)
            }
            usersService = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create(Gson()))
                .baseUrl(mockServer.url("").toString())
                .client(OkHttpClient.Builder().build())
                .build()
                .create(IUsersService::class.java)
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