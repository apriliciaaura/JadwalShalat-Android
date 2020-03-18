package com.example.masjid

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_slideshow.*
import kotlinx.android.synthetic.main.list_layout.*
import org.json.JSONObject

class Slideshow : AppCompatActivity() {

    val context = this

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slideshow)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val users=ArrayList<User>()

    AndroidNetworking.get("http://192.168.0.6/jamsholat/slideshow-json.php")
    .setPriority(Priority.MEDIUM)
    .build()
    .getAsJSONObject(object : JSONObjectRequestListener {
        override fun onResponse(response: JSONObject) {
            Log.e("_kotlinResponse", response.toString())

            val jsonArray = response.getJSONArray("result")
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                Log.e("_kotlinTitle", jsonObject.optString("id_slideshow"))

                var isi1=jsonObject.optString("judul_slideshow").toString()
                var isi2=jsonObject.optString("url_slideshow").toString()

                users.add(User("$isi1", "$isi2"))
            }

            val adapter=CustomAdapter(users)
            recyclerView.adapter=adapter
        }

        override fun onError(anError: ANError) {
            Log.i("_err", anError.toString())
        }
    })
}
}