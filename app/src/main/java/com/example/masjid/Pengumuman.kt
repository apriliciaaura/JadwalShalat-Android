package com.example.masjid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_identitas.*
import kotlinx.android.synthetic.main.activity_pengumuman.*
import kotlinx.android.synthetic.main.activity_pengumuman.kembali
import kotlinx.android.synthetic.main.activity_pengumuman.simpan
import org.json.JSONObject

class Pengumuman : AppCompatActivity(){

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman)

        simpan.setOnClickListener {
            var isi_pengumuman = isi2.text.toString()

            postkeserver(isi_pengumuman)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        kembali.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        getdariserver()
    }

    fun getdariserver(){
        AndroidNetworking.get("http://192.168.0.6/jamsholat/pengumuman-json.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) { // do anything with response
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray= response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject= jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("judul_pengumuman"))

                        judul.setText(jsonObject.optString("judul_pengumuman"))
                        isi.setText(jsonObject.optString("isi_pengumuman"))
                    }
                }

                override fun onError(anError: ANError) { // handle error
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postkeserver(data1:String){
        AndroidNetworking.post("http://192.168.0.6/jamsholat/update-pengumuman.php")
            .addBodyParameter("isi_pengumuman", data1)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject){

                }

                override fun onError(anError: ANError?) {

                }
            })
    }
}
