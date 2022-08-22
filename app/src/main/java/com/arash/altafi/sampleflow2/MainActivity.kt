package com.arash.altafi.sampleflow2

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.request.Disposable
import coil.transform.RoundedCornersTransformation
import com.arash.altafi.sampleflow2.databinding.ActivityMainBinding
import com.arash.altafi.sampleflow2.utils.NetworkResult
import com.arash.altafi.sampleflow2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var _binding: ActivityMainBinding
    private lateinit var disposable: Disposable
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        fetchData()
        _binding.imgRefresh.setOnClickListener {
            fetchResponse()
        }
    }

    private fun fetchResponse() {
        mainViewModel.fetchDogResponse()
        _binding.pbDog.visibility = View.VISIBLE
    }

    private fun fetchData() {
        fetchResponse()
        mainViewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        imageUrl = response.data.message
                        _binding.imgDog.load(
                            response.data.message
                        ) {
                            transformations(RoundedCornersTransformation(16f))
                        }
                    }
                    _binding.pbDog.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    _binding.pbDog.visibility = View.GONE
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    _binding.pbDog.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.flow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_collect -> {
                lifecycleScope.launch {
                    flow {
                        emit(1)
                        delay(50)
                        emit(2)
                    }.flowOn(Dispatchers.IO)
                        .collect { value ->
                            Log.i("test123321", "collect => Collecting $value")
                            delay(100)
                            Log.i("test123321", "collect => $value collected")
                        }
                }
                //prints "Collecting 1, 1 collected, Collecting 2, 2 collected"
            }
            R.id.action_collect_latest -> {
                lifecycleScope.launch {
                    flow {
                        emit(1)
                        delay(50)
                        emit(2)
                    }.flowOn(Dispatchers.IO)
                        .collectLatest { value ->
                            Log.i("test123321", "collectLatest => Collecting $value")
                            delay(100)
                            Log.i("test123321", "collectLatest => $value collected")
                        }
                }
                //prints "Collecting 1, Collecting 2, 2 collected"
            }
        }
        return super.onOptionsItemSelected(item)
    }

}