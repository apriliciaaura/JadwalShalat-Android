package com.example.masjid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_identitas.*
import org.json.JSONObject

class Identitas : AppCompatActivity(){

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identitas)

        val context = this

        simpan.setOnClickListener {
            var nama_masjid = nama2.text.toString()
            var alamat_masjid = alamat2.text.toString()

            postkeserver(nama_masjid, alamat_masjid)

            val intent = getIntent()
            startActivity(intent)
            finish()
        }

        kembali.setOnClickListener{
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        getdariserver()

    }

    fun getdariserver(){
        AndroidNetworking.get("http://192.168.0.6/jamsholat/identitas-json.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) { // do anything with response
                    Log.e("_kotlinResponse", response.toString())

                    val jsonArray= response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject= jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("nama_masjid"))

                        nama.setText(jsonObject.optString("nama_masjid"))
                        alamat.setText(jsonObject.optString("alamat_masjid"))
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }

    fun postkeserver(data1:String, data2:String){

        AndroidNetworking.post("http://192.168.0.6/jamsholat/update-identitas.php")
            .addBodyParameter("nama_masjid", data1)
            .addBodyParameter("alamat_masjid", data2)
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
