package com.duy.githubclients.presentation.view.detail_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.duy.githubclients.R
import com.duy.githubclients.databinding.UserDetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment() {

    private val userDetailViewModel: UserDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: UserDetailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.user_detail_fragment, container, false)
        binding.userDetailVM = userDetailViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            val username: String = UserDetailFragmentArgs.fromBundle(it).username
            userDetailViewModel.getUserInfoByUsername(username)
        }

        userDetailViewModel.pageUrl.observe(viewLifecycleOwner, Observer {
            openInBrowser(it)
        })
    }

    private fun openInBrowser(pageUrl: String?) {
        pageUrl?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }
}