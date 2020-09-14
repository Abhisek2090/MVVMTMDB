package com.example.moviemvvm.movie.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemvvm.R
import com.example.moviemvvm.databinding.FragmentMoviesBinding
import com.example.moviemvvm.di.Injectable
import com.example.moviemvvm.di.injectViewModel

import com.example.moviemvvm.ui.GridSpacingItemDecoration
import com.example.moviemvvm.ui.VerticalItemDecoration
import com.example.moviemvvm.ui.hide
import com.example.moviemvvm.utils.ConnectivityUtil
import javax.inject.Inject

class MoviesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MovieViewModel

    private lateinit var binding:FragmentMoviesBinding
    private val adapter: MovieAdapter by lazy { MovieAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var gridLayoutManager: GridLayoutManager

    private var isLinearLayoutManager: Boolean = false

    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
            resources.getDimension(R.dimen.margin_normal).toInt())
    }

    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
            SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context!!)
        viewModel.title = "star wars"

        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
        setLayoutManager()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_representation, menu)
        setDataRepresentationIcon(menu.findItem(R.id.list))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.list -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setDataRepresentationIcon(item)
                setLayoutManager()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: MovieAdapter) {
        viewModel.movies.observe(viewLifecycleOwner) {
            binding.progressBar.hide()
            adapter.submitList(it)
        }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.recyclerView

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun setDataRepresentationIcon(item: MenuItem) {
        item.setIcon(if (isLinearLayoutManager)
            R.drawable.ic_grid_list_24dp else R.drawable.ic_list_white_24dp)
    }


    companion object {
        const val SPAN_COUNT = 2;
    }
}