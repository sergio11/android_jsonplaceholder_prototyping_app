package sanchez.sanchez.sergio.postsapp.database.posts

import android.os.Build
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import sanchez.sanchez.sergio.postsapp.domain.models.Comment
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.domain.models.User
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.exception.DBNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.IPostDBRepository
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.PostDBRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.db.room.AppRoomDatabase
import sanchez.sanchez.sergio.postsapp.persistence.db.room.mapper.PostDBMapper

/**
 * Post Database Unit Test
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class PostsDatabaseRepositoryUTest {

    private lateinit var database: AppRoomDatabase
    private lateinit var postDBRepository: IPostDBRepository

    private val postToBeCache = PostDetail(
        id = 2L,
        title = "Post Title",
        body = "Post Body",
        author = User(
            id = 3L,
            name = "Sergio Sánchez",
            username = "ssanchez",
            email = "email@gmail.com",
            address = "Calle Falsa 123",
            latitude = -23.45454,
            longitude = 12.454545,
            phone = "691885235",
            website = "www.ssanchez.com",
            company = "Company S.L.U"
        ),
        commentList = listOf(
            Comment(
                id = 1L,
                name = "David Martín",
                email = "davig@gmail.com",
                body = "Esto es un comentario de prueba 1"
            ),
            Comment(
                id = 2L,
                name = "Jose Fidalgo",
                email = "josefida@gmail.com",
                body = "Esto es un comentario de prueba 2"
            )
        )
    )

    @Test
    fun test_database_repository_001_the_post_has_not_cached_yet() {
        runBlocking {
            try {
                val postId = 1L
                withContext(IO) {
                    postDBRepository.findById(postId)
                }
            } catch (ex: Exception) {
                // Check No Result Exception
                assertThat(ex.cause).isInstanceOf(DBNoResultException::class.java)
                assertThat(ex).isInstanceOf(DBNoResultException::class.java)
            }
        }
    }

    @Test
    fun test_database_repository_002_the_post_has_cached_successfully() {
        runBlocking {

            val postDetailInDB = withContext(IO) {
                postDBRepository.save(postToBeCache)
                postDBRepository.findById(postToBeCache.id)
            }

            assertThat(postDetailInDB.id).isEqualTo(postToBeCache.id)
            assertThat(postDetailInDB.title).isEqualTo(postToBeCache.title)
            assertThat(postDetailInDB.body).isEqualTo(postToBeCache.body)
            assertThat(postDetailInDB.author.id).isEqualTo(postToBeCache.author.id)
            assertThat(postDetailInDB.author.name).isEqualTo(postToBeCache.author.name)
            assertThat(postDetailInDB.author.username).isEqualTo(postToBeCache.author.username)
            assertThat(postDetailInDB.author.email).isEqualTo(postToBeCache.author.email)
            assertThat(postDetailInDB.author.website).isEqualTo(postToBeCache.author.website)
            assertThat(postDetailInDB.author.phone).isEqualTo(postToBeCache.author.phone)
            assertThat(postDetailInDB.author.company).isEqualTo(postToBeCache.author.company)
            assertThat(postDetailInDB.author.address).isEqualTo(postToBeCache.author.address)
            assertThat(postDetailInDB.author.latitude).isEqualTo(postToBeCache.author.latitude)
            assertThat(postDetailInDB.author.longitude).isEqualTo(postToBeCache.author.longitude)
            assertThat(postDetailInDB.commentList).isNotEmpty
            assertThat(postDetailInDB.commentList).hasSize(postToBeCache.commentList.size)

            for(index in postToBeCache.commentList.indices) {

                assertThat(postDetailInDB.commentList[index].id).isEqualTo(postToBeCache.commentList[index].id)
                assertThat(postDetailInDB.commentList[index].name).isEqualTo(postToBeCache.commentList[index].name)
                assertThat(postDetailInDB.commentList[index].body).isEqualTo(postToBeCache.commentList[index].body)
                assertThat(postDetailInDB.commentList[index].email).isEqualTo(postToBeCache.commentList[index].email)

            }
        }

    }

    @Test
    fun test_database_repository_003_posts_cached_can_be_delete_successfully() {
        runBlocking {
            try {
                withContext(IO) {
                    postDBRepository.save(postToBeCache)
                    postDBRepository.deleteAll()
                    postDBRepository.findById(postToBeCache.id)
                }
            } catch (ex: Exception) {
                // Check No Result Exception
                assertThat(ex).isInstanceOf(DBNoResultException::class.java)
            }
        }
    }


    /**
     * Setup
     */
    @Before
    @Throws
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppRoomDatabase::class.java).build()

        postDBRepository = PostDBRepositoryImpl(
            database.postDAO(),
            PostDBMapper()
        )

    }

    /**
     * Tear Down
     */
    @After
    @Throws
    fun tearDown() {
        database.close()
    }
}