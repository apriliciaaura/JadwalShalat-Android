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
import kotlinx.android.synthetic.main.activity_jadwal.*
import kotlinx.android.synthetic.main.activity_jadwal.simpan
import org.json.JSONObject

class Jadwal : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        simpan.setOnClickListener {
            var shubuh = shubuh.text.toString()
            var dhuha = dhuha.text.toString()
            var dhuhur = dhuhur.text.toString()
            var ashar = ashar.text.toString()
            var maghrib = maghrib.text.toString()
            var isha = isha.text.toString()

            postkeserver(shubuh, dhuha, dhuhur, ashar, maghrib, isha)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        balik.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        getdariserver()
    }

    fun getdariserver(){
        AndroidNetworking.get("http://192.168.0.6/jamsholat/jadwal-json.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) { // do anything with response
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray= response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject= jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("shubuh"))

                        txt1.setText(jsonObject.optString("shubuh"))
                        txt6.setText(jsonObject.optString("dhuha"))
                        txt2.setText(jsonObject.optString("dhuhur"))
                        txt3.setText(jsonObject.optString("ashar"))
                        txt4.setText(jsonObject.optString("maghrib"))
                        txt5.setText(jsonObject.optString("isha"))
                    }
                }

                override fun onError(anError: ANError) { // handle error
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postkeserver(data1:String, data2:String, data3:String, data4:String, data5:String, data6:String){

        AndroidNetworking.post("http://192.168.0.6/jamsholat/update-jadwal.php")
            .addBodyParameter("shubuh", data1)
            .addBodyParameter("dhuha", data2)
            .addBodyParameter("dhuhur", data3)
            .addBodyParameter("ashar", data4)
            .addBodyParameter("maghrib", data5)
            .addBodyParameter("isha", data6)
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