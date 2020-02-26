package com.e.occanotestsidep.ui.auth.login

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.e.occanotestsidep.utils.Single
import kotlinx.android.synthetic.main.fragment_calibration_fragment_z.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.collections.ArrayList

class LoginFragment : Fragment(),LoginAdapter.Interaction, View.OnClickListener{

    private var imo: String? = null
    var ip:String? = null
    var selectedShip = Ship()
    var ship1 = Ship()
    var ship2 = Ship()
    var ship3 = Ship()
    var ship4 = Ship()
    var ship5 = Ship()
    var ship6 = Ship()
    var ship7 = Ship()
    var ship8 = Ship()

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
        ship1 = Ship(0, "ANAFI WARRIOR",	"MITSUI MAN B&W 6S60 MC-C","4071.5",6,"s","60","c",	"c","9370848",	"Tanker","2009","243.8","42","107593","60205",false)
        ship2 = Ship(1, "GREEN WARRIOR",	"MITSUI MAN B&W 6S60 MC-C","4071.5",6,	"s",	"60"	,"c"	,"c"	,"9514169"	,"Tanker"	,"2011"	,"229"	,"42.04"	,"104626"	,"56326", false)
        ship3 = Ship(2, "PATMOS WARRIOR","D.U. SULZER 6RTA58T","3830",6,"4.17","58","","","9337418",	"Tanker",	"2007","239","42.03","105572","56172",false)
        ship4 = Ship(3, "SYROS WARRIOR","MITSUI MAN B&W 6S60 MC-C","4071.5",6,"s",	"60",	"c"	,"c"	,"9370850"	,"Tanker"	,"2009"	,"243.8",	"42","107687",	"60205",false)
        ship5 = Ship(4, "DILIGENT WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9750050",	"Tanker",	"2016",	"274.22","48",  "149992",	"81287", false)
        ship6 = Ship(5, "FAITHFUL WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9750062",	"Tanker",	"2016",	"274.22","48",  "149992",	"81287", false)
        ship7 = Ship(6, "PRUDENT WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9753545",	"Tanker",	"2017",	"274",	   "48","149992",	"81287", false)
        ship8 = Ship(7, "RELIABLE WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9753557",	"Tanker",	"2017",	"274.22","48",  "149992",	"81287", false)
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
        selectedShip = item

        loginAdapter.notifyDataSetChanged()
    }

//    private fun submit(data: String) {
//        //        String URL= Const.URL_OCCANO_REGISTER;
//        val URL = "http://"+ip+"/register/"
//        requestQueue = Volley.newRequestQueue(
//            Objects.requireNonNull(context)
//        )
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST,
//            URL,
//            Response.Listener { response ->
//                try {
//                    val objres = JSONObject(response)
//                    Toast.makeText(
//                        Objects.requireNonNull(
//                            context
//                        ), objres.toString(), Toast.LENGTH_LONG
//                    ).show()
//                } catch (e: JSONException) {
//                    Toast.makeText(
//                        context,
//                        "Server Error",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    //                    Log.d(TAG, "onResponse: Server Error");
//                }
//                //Log.i("VOLLEY", response);
//            },
//            Response.ErrorListener { error ->
//                if (error != null && error.message != null) { //                    Log.d(TAG, "onResponse: Faild beacause: %s".format(error.toString()));
//                } else if (error is TimeoutError || error is NoConnectionError) {
//                    Toast.makeText(
//                        context,
//                        "please reconnect to corti",
//                        Toast.LENGTH_LONG
//                    ).show()
////                    findNavController()
////                        .navigate(R.id.action_loginFragment_to_loadFragment)
//                }
//                Toast.makeText(
//                    Objects.requireNonNull(
//                        context
//                    ), error.message, Toast.LENGTH_SHORT
//                ).show()
//                //Log.v("VOLLEY", error.toString());
//            }) {
//            override fun getBodyContentType(): String {
//                return "application/json; charset=utf-8"
//            }
//
//            @Throws(AuthFailureError::class)
//            override fun getBody(): ByteArray {
//                return try {
//                    data.toByteArray(charset("utf-8"))
//                } catch (uee: UnsupportedEncodingException) { //Log.v("Unsupported Encoding while trying to get the bytes", data);
//                    uee.message!!.toByteArray()
//                }
//            }
//        }
//        requestQueue!!.add(stringRequest)
//    }


    private fun submit(data: String) {
        val URL = "http://"+ip+"/register"
//        val URL = "http://192.168.1.120:5000/register"
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
//
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

            override fun getParamsEncoding(): String {
                return super.getParamsEncoding()
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



//    fun submit(ship:Ship){
////        val requestUrl = "http://"+ip+"/register/"
//        val requestUrl = "http://192.168.1.120:5000/register"
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST,
//            requestUrl,
//            Response.Listener { _ ->
//                //                    Log.e(
////                        "Volley Result",
////                        "" + response
////                    ) //the response contains the result from the server, a json string or any other object returned by your server
//            },
//            Response.ErrorListener { error ->
//                error.printStackTrace() //log the error resulting from the request for diagnosis/debugging
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val postMap: MutableMap<String, Any> = HashMap()
//
//               postMap["msgtype"]="login"
//               postMap["vessel"]=ship.vessel.toString()
//               postMap["m_e"]=ship.m_e.toString()
//               postMap["displacement_engine"]=ship.displacement_engine.toString().toFloat()
//               postMap["Number_of_cylinders"]=ship.Number_of_cylinders.toString().toInt()
//               postMap["Stroke_bore_ratio"]=ship.Stroke_bore_ratio.toString()
//               postMap["Diameter_of_piston_in_cm"]=ship.Diameter_of_piston_in_cm.toString().toFloat()
//               postMap["Concept"]=ship.Concept.toString()
//               postMap["Design"]=ship.Design.toString()
//               postMap["IMO"]=ship.IMO.toString()
//               postMap["AIS_Vessel_Type"]=ship.AIS_Vessel_Type.toString()
//               postMap["Year_Built"]=ship.Year_Built.toString().toInt()
//               postMap["Length_Overall"]=ship.Length_Overall.toString().toFloat()
//               postMap["Breadth_Extreme"]=ship.Breadth_Extreme.toString()
//               postMap["Deadweight"]=ship.Deadweight.toString().toFloat()
//               postMap["Gross_Tonnage"]=ship.Gross_Tonnage.toString().toFloat()

//                Log.d("",(postMap.toString()))
////                Toast.makeText(context,postMap.toString(), Toast.LENGTH_LONG).show()
//                   return postMap
//            }
//        }
//        Single.getInstance(this.context).addToRequestQueue(stringRequest)
//    }


    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.buttonLogin -> {
              try {
                  val data =  //

                  "{" +
                          "\"msgtype\":" + "\"login\"" + ","  +
                          "\"vessel\":" +  "\""+ selectedShip.vessel.toString() +"\""+ "," +
                          "\"m_e\":" +"\""+ selectedShip.m_e.toString() +"\""+ ","  +
                          "\"displacement_engine\":" + selectedShip.displacement_engine!!.toFloat() + "," +
                          "\"Number_of_cylinders\":" + selectedShip.Number_of_cylinders!!.toInt()  + ","  +
                          "\"Stroke_bore_ratio\":" +"\""+ selectedShip.Stroke_bore_ratio.toString() +"\""+ ","  +
                          "\"Diameter_of_piston_in_cm\":" + selectedShip.Diameter_of_piston_in_cm!!.toFloat() + "," +
                          "\"Concept\":" +"\""+ selectedShip.Concept!!.toString() +"\""+ ","  +
                          "\"Design\":" +"\""+ selectedShip.Design.toString() +"\""+ ","  +
                          "\"IMO\":" +"\""+ selectedShip.IMO.toString() +"\""+ ","  +
                          "\"AIS_Vessel_Type\":" +"\""+ selectedShip.AIS_Vessel_Type.toString() +"\""+ ","  +
                          "\"Year_Built\":" + selectedShip.Year_Built!!.toInt() + ","  +
                          "\"Length_Overall\":" + selectedShip.Length_Overall.toString().toFloat() + ","  +
                          "\"Gross_Tonnage\":" + selectedShip.Gross_Tonnage.toString().toFloat() + ","  +
                          "\"Breadth_Extreme\":" +"\""+ selectedShip.Breadth_Extreme.toString() +"\""+ ","  +
                          "\"Deadweight\":" + selectedShip.Deadweight.toString().toFloat() + ","  +
                          "\"IMO\":" + selectedShip.Gross_Tonnage.toString().toFloat() +
                          "}"

                  submit(data)
                  saveShipProperties()
//                  Toast.makeText(context, data, Toast.LENGTH_LONG).show()
              }  catch (e:Error){
                  e.printStackTrace()
              }
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_loadFragment)
            }
        }
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

    fun saveShipProperties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("nameOfCurrentShip", selectedShip.vessel)
            putString("nameOfCurrentEngine", selectedShip.m_e)
            commit()
        }
    }
}