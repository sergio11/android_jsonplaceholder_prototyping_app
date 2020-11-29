package sanchez.sanchez.sergio.postsapp.ui.features.posts.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.post_detail_author_layout.*
import kotlinx.android.synthetic.main.post_detail_body_layout.*
import kotlinx.android.synthetic.main.post_detail_comments_layout.*
import kotlinx.android.synthetic.main.post_detail_content_layout.*
import kotlinx.android.synthetic.main.post_detail_layout.*
import sanchez.sanchez.sergio.postsapp.R
import sanchez.sanchez.sergio.postsapp.di.component.post.PostDetailFragmentComponent
import sanchez.sanchez.sergio.postsapp.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.domain.models.User
import sanchez.sanchez.sergio.postsapp.ui.core.SupportFragment
import sanchez.sanchez.sergio.postsapp.ui.core.ext.popBackStack
import timber.log.Timber

/**
 * Post Detail Fragment
 */
class PostDetailFragment: SupportFragment<PostDetailViewModel>(PostDetailViewModel::class.java),
    OnMapReadyCallback {

    private val fragmentComponent: PostDetailFragmentComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerComponentFactory.getPostDetailFragmentComponent(requireActivity() as AppCompatActivity)
    }

    private val args by navArgs<PostDetailFragmentArgs>()

    lateinit var recyclerViewAdapter: PostCommentsListAdapter
    private var googleMap: GoogleMap? = null

    override fun layoutId(): Int = R.layout.post_detail_layout

    override fun onInject() {
        fragmentComponent.inject(this)
    }

    override fun onObserverLiveData(viewModel: PostDetailViewModel) {
        super.onObserverLiveData(viewModel)
        // Observe operation result
        viewModel.postDetailState.observe(this, {
            when(it) {
                is PostDetailState.OnSuccess -> onLoadSuccessfully(it.postDetail)
                is PostDetailState.OnError -> onErrorOccurred(it.ex)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Recycler View Adapter
        recyclerViewAdapter = PostCommentsListAdapter(requireContext(), mutableListOf())
        // Configure Recycler View
        commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            adapter = recyclerViewAdapter
        }

        // Init Map Asyc
        val mapFragment =  childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Load Post Detail
        viewModel.loadById(args.postId)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        googleMap?.let { mMap ->
            configureMap(mMap)
        }
    }

    /**
     * Private Methods
     */

    /**
     * Configure Map
     * @param mMap
     */
    private fun configureMap(mMap: GoogleMap) {
        // Map Default Settings
        mMap.apply {
            setMinZoomPreference(DEFAULT_MIN_ZOOM_LEVEL)
            setMaxZoomPreference(DEFAULT_MAX_ZOOM_LEVEL)
        }
    }

    /**
     * on Load Successfully
     * @param postDetail
     */
    @SuppressLint("QueryPermissionsNeeded")
    private fun onLoadSuccessfully(postDetail: PostDetail) {
        postTitleTextView.text = postDetail.title
        postBodyTextView.text = postDetail.body
        postAuthorNameListItem.valueText = postDetail.author.name
        postAuthorUsernameListItem.valueText = postDetail.author.username
        postAuthorAddressListItem.valueText = postDetail.author.address
        postAuthorCompanyListItem.valueText = postDetail.author.company
        postAuthorEmailListItem.valueText = postDetail.author.email
        postAuthorPhoneListItem.valueText = postDetail.author.phone
        postAuthorWebsiteListItem.valueText = postDetail.author.website
        configureAuthorMapMarker(postDetail.author)
        recyclerViewAdapter.replaceData(postDetail.commentList)

        contentView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
    }

    /**
     * on Error Occurred
     * @param ex
     */
    private fun onErrorOccurred(ex: Exception) {
        Timber.d("OnErrorOccurred -> ${ex.message}")
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(resources.getString(R.string.post_detail_error))
            .setCancelable(false)
            .setOnKeyListener { _, _, _ -> false }
            .setPositiveButton(resources.getString(R.string.post_detail_error_accept_button)) { _, _ ->
                popBackStack()
            }
            .show()
    }

    /**
     * Configure Author Map Marker
     * @param author
     */
    private fun configureAuthorMapMarker(author: User) {

        googleMap?.apply {

            val authorLocation = LatLng(author.latitude, author.longitude)

            addMarker(MarkerOptions()
                .position(authorLocation)
                .title("location of ${author.name}")
                .snippet(author.address)
                .visible(true)
            ).apply {
                showInfoWindow()
                setOnInfoWindowClickListener {
                    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${authorLocation.latitude},${authorLocation.longitude}")).apply {
                        setPackage("com.google.android.apps.maps")
                    }
                    if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(mapIntent)
                    }
                }
                moveCamera(CameraUpdateFactory.newLatLngZoom(authorLocation, DEFAULT_ZOOM_LEVEL))
            }
        }
    }


    companion object {
        private const val DEFAULT_ZOOM_LEVEL = 18.0f
        private const val DEFAULT_MIN_ZOOM_LEVEL = 6.0f
        private const val DEFAULT_MAX_ZOOM_LEVEL = 22.0f
    }


}