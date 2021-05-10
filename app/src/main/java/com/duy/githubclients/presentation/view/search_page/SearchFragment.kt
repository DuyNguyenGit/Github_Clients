package com.duy.githubclients.presentation.view.search_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duy.githubclients.R
import com.duy.githubclients.databinding.SearchFragmentBinding
import com.duy.githubclients.util.hideKeyboard
import ir.sdrv.mobilletsample.data.remote.api.base.Status
import ir.sdrv.mobilletsample.data.remote.api.models.GithubUserModel
import ir.sdrv.mobilletsample.presentation.datasource.UsersListAdapter
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), UsersListAdapter.UsersListAdapterInteraction {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var itemViewer: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SearchFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)

        binding.searchVM = searchViewModel
        binding.lifecycleOwner = this
        itemViewer = binding.root.findViewById(R.id.recycler_view)
        initAdapterAndObserve()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.queryHint = getString(R.string.search_hint)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.search(query)
                activity?.hideKeyboard()
            }

            override fun onQueryTextChange(p0: String?): Boolean {

            }

        })
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

        val direction = SearchFragmentDirections.actionSearchFragmentToUserDetailFragment(githubUserModel.login)
        findNavController().navigate(direction)
    }
}
