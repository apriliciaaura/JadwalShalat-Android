package com.example.masjid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(){

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        marquee.setSelected(true)
        getdariserver();

        btn_jadwal.setOnClickListener {
            startActivity(Intent(this, Jadwal::class.java))
        }

        btn_identitas.setOnClickListener {
            startActivity(Intent(this, Identitas::class.java))
        }

        btn_pengumuman.setOnClickListener {
            startActivity(Intent(this, Pengumuman::class.java))
        }

        btn_marquee.setOnClickListener {
            startActivity(Intent(this, Marquee::class.java))
        }

        btn_tagline.setOnClickListener {
            startActivity(Intent(this, Tagline::class.java))
        }

        btn_slideshow.setOnClickListener {
            startActivity(Intent(this, Slideshow::class.java))
        }
    }



        fun getdariserver(){
            AndroidNetworking.get("http://192.168.0.6/jamsholat/marquee-json.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) { // do anything with response
                        Log.e("_kotlinResponse", response.toString())

                        val jsonArray= response.getJSONArray("result")
                        for (i in 0 until jsonArray.length()){
                            val jsonObject= jsonArray.getJSONObject(i)
                            Log.e("_kotlinTitle", jsonObject.optString("isi_marquee"))

                            marquee.setText(jsonObject.optString("isi_marquee"))
                        }
                    }

                    override fun onError(anError: ANError) { // handle error
                        Log.i("_err", anError.toString())
                    }
                })
        }
}