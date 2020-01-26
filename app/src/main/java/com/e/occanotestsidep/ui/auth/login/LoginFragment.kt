package com.e.occanotestsidep.ui.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Ship
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.collections.ArrayList

class LoginFragment : Fragment(),LoginAdapter.Interaction, View.OnClickListener{

    private var imo: String? = null
    var ip:String? = null
    private var requestQueue: RequestQueue? = null
    var shipList =  ArrayList<Ship>()
    lateinit var loginAdapter:LoginAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAddress()
        setListeners(view)
        initRv()
        initList()

        this.loginAdapter.submitList(shipList)
    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.buttonLogin).setOnClickListener(this)
    }

    private fun initRv() {
        rv_login.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            loginAdapter = LoginAdapter(this@LoginFragment)
            adapter = loginAdapter
        }

    }

    private fun initList() {
        val ship1 = Ship(0, "9310525", "Kythira warrior", "MAN 6S60MC-C",false)
        val ship2 = Ship(1, "9514169", "Green warrior", "MAN 6S60MC-C",false)
        val ship3 = Ship(2, "9753557", "Reliable warrior", "Huyndai –man B&W 6G70ME-c9.5",false)
        val ship4 = Ship(3, "9753545", "Prudent warrior", "Huyndai –man B&W 6G70ME-c9.5",false)
        val ship5 = Ship(4, "9750050", "Diligent warrior", "Huyndai –man B&W 6G70ME-c9.5",false)
        val ship6 = Ship(5, "9370850", "Syros warrior", "MITSUI-MAN B&R 6S60MC-C",false)
        val ship7 = Ship(6, "9471202", "Zim constanza", "MAN 8K90MC-C6",false)
        val ship8 = Ship(7, "9471214", "ZIM Tarragona", "",false)
        shipList.add(ship1)
        shipList.add(ship2)
        shipList.add(ship3)
        shipList.add(ship4)
        shipList.add(ship5)
        shipList.add(ship6)
        shipList.add(ship7)
        shipList.add(ship8)
    }

    override fun onItemSelected(position: Int, item: Ship) {

        for (stamShip: Ship in shipList){
            stamShip.isSelected = false
        }
        item.isSelected = true
        imo = item.imo

        loginAdapter.notifyDataSetChanged()
    }

    private fun submit(data: String) {
        //        String URL= Const.URL_OCCANO_REGISTER;
        val URL = "http://"+ip+"/register/"
        requestQueue = Volley.newRequestQueue(
            Objects.requireNonNull(context)
        )
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            URL,
            Response.Listener { response ->
                try {
                    val objres = JSONObject(response)
                    Toast.makeText(
                        Objects.requireNonNull(
                            context
                        ), objres.toString(), Toast.LENGTH_LONG
                    ).show()
                } catch (e: JSONException) {
                    Toast.makeText(
                        context,
                        "Server Error",
                        Toast.LENGTH_LONG
                    ).show()
                    //                    Log.d(TAG, "onResponse: Server Error");
                }
                //Log.i("VOLLEY", response);
            },
            Response.ErrorListener { error ->
                if (error != null && error.message != null) { //                    Log.d(TAG, "onResponse: Faild beacause: %s".format(error.toString()));
                } else if (error is TimeoutError || error is NoConnectionError) {
                    Toast.makeText(
                        context,
                        "please reconnect to corti",
                        Toast.LENGTH_LONG
                    ).show()
//                    findNavController()
//                        .navigate(R.id.action_loginFragment_to_loadFragment)
                }
                Toast.makeText(
                    Objects.requireNonNull(
                        context
                    ), error.message, Toast.LENGTH_SHORT
                ).show()
                //Log.v("VOLLEY", error.toString());
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return try {
                    data.toByteArray(charset("utf-8"))
                } catch (uee: UnsupportedEncodingException) { //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    uee.message!!.toByteArray()
                }
            }
        }
        requestQueue!!.add(stringRequest)
    }

    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.buttonLogin -> {
                val data =  //                "[\n" +
                    "{\n" +
                            "\"msgtype\":" + "\"login\"" + "," + "\n" +
                            "\"imo\":" + "\"" + imo + "\"" + "\n" +
                            "}\n" //                + "]"
                submit(data)
                Toast.makeText(context, data, Toast.LENGTH_LONG)
                    .show()
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_loadFragment)
            }
        }
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }
}