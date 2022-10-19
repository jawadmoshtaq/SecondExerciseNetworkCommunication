package com.example.secondexerciseofnetworkcommjunication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.secondexerciseofnetworkcommjunication.databinding.ActivityMainBinding
import com.example.secondexerciseofnetworkcommjunication.model.Dog
import com.example.secondexerciseofnetworkcommjunication.network.DogApi
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = OkHttpClient()
        val url = "https://dog.ceo/api/breeds/image/random"
        val request = Request.Builder()
            .url(url)
            .build()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRetrofit.setOnClickListener {
            DogApi.apiService.getRandomDog().enqueue(object : Callback<Dog> {
                override fun onResponse(call: Call<Dog>, response: Response<Dog>) {
                    binding.textViewOk.text = response.body().toString()
                }

                override fun onFailure(call: Call<Dog>, t: Throwable) {
                    Snackbar.make(
                        findViewById(R.id.main_layout),
                        "No network connection",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") { call }.show()
                    Log.d("MainActivity", "Value received, $it")
                }

            })
        }

        binding.btnOkhttp3.setOnClickListener {

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                    Snackbar.make(
                        findViewById(R.id.main_layout),
                        "No network connection",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") { call }.show()
                    Log.d("MainActivity", "Value received, $it")
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        //converts data to a readable form
                        val jsonData: ResponseBody? = response.body
                        val result = jsonData?.string()?.let { it1 -> JSONObject(it1) }
                        //setting data to the ui thread
                        runOnUiThread {
                            binding.textViewOk.text = result.toString()
                        }
                    }
                }
            })
        }
    }
}

