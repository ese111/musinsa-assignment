package com.example.musinsa.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musinsa.R
import com.example.musinsa.common.Logger
import com.example.musinsa.common.UiState
import com.example.musinsa.databinding.ActivityHomeBinding
import com.example.musinsa.ui.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = HomeAdapter()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeData.collect {
                    when(it) {
                        is UiState.Error -> Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        is UiState.Success -> {
                            adapter.submitList(it.data)
                            Logger("Success ${it.data.size}")
                        }
                        is UiState.Loading -> Logger("Loading")
                        is UiState.Empty -> Logger("empty")
                    }
                }
            }
        }

        binding.rvHomeView.adapter = adapter
    }
}