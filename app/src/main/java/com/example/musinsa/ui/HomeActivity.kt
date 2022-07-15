package com.example.musinsa.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.musinsa.common.logger
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

        val adapter = HomeAdapter(this) { link ->
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("link", link)
            startActivity(intent)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeData.collect {
                    when(it) {
                        is UiState.Error -> Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        is UiState.Success -> {
                            adapter.submitList(it.data)
                            logger("Success ${it.data.size}")
                        }
                        is UiState.Loading -> logger("Loading")
                        is UiState.Empty -> logger("empty")
                    }
                }
            }
        }

        binding.rvHomeView.adapter = adapter
    }
}