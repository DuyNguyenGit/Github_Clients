package com.duy.githubclients.presentation.view.search_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duy.githubclients.R
import com.duy.githubclients.data.remote.api.models.GithubUserModel
import com.duy.githubclients.databinding.SearchFragmentBinding
import com.duy.githubclients.presentation.view.search_page.datasource.UsersListAdapter
import com.duy.githubclients.util.onQueryTextChange
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), UsersListAdapter.UsersListAdapterInteraction {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var itemViewer: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SearchFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)

        binding.searchVM = searchViewModel
        binding.lifecycleOwner = this
        itemViewer = binding.root.findViewById(R.id.recycler_view)
        initAdapterAndObserve()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.queryHint = getString(R.string.search_hint)
        searchViewModel.search("duynguyen")
        search_view.onQueryTextChange { query ->
            searchViewModel.search(query)
        }
    }

    private fun initAdapterAndObserve() {
        val usersListAdapter = UsersListAdapter(this)

        itemViewer.layoutManager = LinearLayoutManager(context)
        itemViewer.adapter = usersListAdapter
        searchViewModel.setUpObservers(this)

        searchViewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            usersListAdapter.submitList(it)
        })
    }

    override fun onUserItemClick(githubUserModel: GithubUserModel) {

        val direction =
            SearchFragmentDirections.actionSearchFragmentToUserDetailFragment(githubUserModel.login)
        findNavController().navigate(direction)
    }
}
