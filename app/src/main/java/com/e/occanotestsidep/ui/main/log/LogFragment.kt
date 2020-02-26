package com.e.occanotestsidep.ui.main.log

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.occanosidetest.models.Log
import com.e.occanosidetest.utils.Run
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.R.color.ACTIVE_COLOR
import com.e.occanotestsidep.persistence.LogRepository
import com.e.occanotestsidep.utils.Single
import kotlinx.android.synthetic.main.fragment_log.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.Error
import java.util.*
import kotlin.collections.ArrayList


class LogFragment : Fragment(), View.OnClickListener,LogAdapter.Interaction{

    private val TAG: String = "log_fragment"
    var logsList = ArrayList<Log>()
    lateinit var logAdapter: LogAdapter
    private var ip: String? = null
    var mLogRepository: LogRepository? = null
    var irValid:Boolean = false
    var micValid: Boolean = false
    var dbValid:Int = 0
    var algoValid: Boolean = true
    lateinit var viewc :View
//    lateinit var itemIr:JSONArray
    lateinit var itemIr:String
    private var requestQueue: RequestQueue? = null
    var isRestartEnabled: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAddress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewc = inflater.inflate(R.layout.fragment_log,container,false)
        return viewc
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLogRepository = LogRepository(context)
        setListeners(view)
        getValidDashboard(viewc)

        initRv()
        retriveLogs()
        logAdapter.submitList(logsList)
        getValidFromApi()
//        getValidAlgoFromApi()
    }

    private fun getValidFromApi() {
        getValidDb()
        getValidDashboard(viewc)
        repeatGetApiData()
    }

    private fun repeatGetApiData() {
//        Run.after(300) {getValidFromApi()}
        Run.after(300000) {getValidFromApi()}

//        Run.after(20000) {getValidDb()}
    }

//    private fun getValidAlgoFromApi() {
//        getValidDashboard()
//        repeatGetAlgoData()
//    }

//    private fun repeatGetAlgoData() {
//        Run.after(20000) {getValidDashFromApi()}
//        Run.after(300000) {getValidAlgoFromApi()}
//    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.btn_log_to_dash).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_log_to_status).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_log_current).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.restart_korti).setOnClickListener(this)
    }

    private fun initRv() {
        log_rv.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            logAdapter = LogAdapter(this@LogFragment)
            adapter = logAdapter
        }
    }

    fun getValidDashboard(view: View){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = false
        algoValid = sharedPref.getBoolean("algo_time_passed", defaultValue)
        irValid = sharedPref.getBoolean("algo_ir_valid", defaultValue)
        micValid = sharedPref.getBoolean("algo_mics_valid", defaultValue)
        setValidDasboardUi(viewc,algoValid,irValid,micValid)
    }


    fun getValidDb(){
    val url  = "http://$ip/records/"

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        Response.Listener { response ->
            android.util.Log.d(TAG,"Response: %s".format(response.toString()))

//            if(response.getInt("'amount'")>0){
            try {
//                itemIr = response.getString("features")

//                irValid = false
//                for (i in 0 until itemIr.length()) {
//                    if ("ir".equals(itemIr.get(i))) {
//                        irValid = true
//                    }
//                }
//                irValid = itemIr.contains("ir")
//                print(itemIr)
//            Toast.makeText(context,itemIr.contains("ir").toString(),Toast.LENGTH_SHORT).show()

//                setValidDasboardUi()

                if (response.getInt("lastUpdateTimePassed")>0){
                    val itemDb = response.getInt("lastUpdateTimePassed")
                    dbValid = itemDb
                    setValidDbUi(dbValid,viewc)
                }
                else{
                    dbValid = 100
                    setValidDbUi(dbValid,viewc)
                }
            }
            catch (e:Error){
                e.printStackTrace()
            }
//            }
//            else{
//                Toast.makeText(context,"נסה שוב עוד מעט".toString(),Toast.LENGTH_SHORT).show()
//            }
//            Toast.makeText(context,itemDb.toString(),Toast.LENGTH_SHORT).show()
//            setValidDbUi(dbValid,viewc)
        },
        Response.ErrorListener { error ->

            if(error != null && error.message != null)
            {
//                    Log.d(TAG, "onResponse: Faild beacause: %s".format(error.toString()))
//                    tv_dash_log.text = "onResponse: Faild beacause: %s".format(error.toString())
                Toast.makeText(context,"הי!! יש בעיה. צור קשר - מרדכי",Toast.LENGTH_SHORT).show()

            }
        }
    )
// Access the RequestQueue through your singleton class.
    Single.getInstance(this.context).addToRequestQueue(jsonObjectRequest)
}

    private fun sendRestartKorti(data:String){

//        val URL= "http://192.168.43.177:4000/shutdown/";
            val URL = "http://"+ip+"/shutdown/"
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
//                        Toast.makeText(
//                            context,
//                            "please restart the korti",
//                            Toast.LENGTH_LONG
//                        ).show()
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


    private fun setValidDbUi(dbValid: Int,view: View) {
        if (dbValid<900000){
            view.findViewById<ImageView>(R.id.iv_valid_db).setImageResource(R.drawable.ic_check_black_24dp)
        }
        else{
            view.findViewById<ImageView>(R.id.iv_valid_db).setImageResource(R.drawable.ic_close_black_24dp)
        }
    }

    private fun setValidDasboardUi(view: View,algoValid:Boolean,irValid:Boolean,micValid:Boolean) {
        if (algoValid){ view.findViewById<ImageView>(R.id.iv_valid_dashboard).setImageResource(R.drawable.ic_check_black_24dp)}
        else{ view.findViewById<ImageView>(R.id.iv_valid_dashboard).setImageResource(R.drawable.ic_close_black_24dp) }
        if (irValid){ view.findViewById<ImageView>(R.id.iv_valid_ir).setImageResource(R.drawable.ic_check_black_24dp) }
        else{ view.findViewById<ImageView>(R.id.iv_valid_ir).setImageResource(R.drawable.ic_close_black_24dp) }
        if (micValid){ view.findViewById<ImageView>(R.id.iv_valid_mics).setImageResource(R.drawable.ic_check_black_24dp) }
        else{ view.findViewById<ImageView>(R.id.iv_valid_mics).setImageResource(R.drawable.ic_close_black_24dp) }
    }


    private fun retriveLogs() {

        this.mLogRepository!!.retriveLogTask()
            .observe(viewLifecycleOwner,
                Observer<List<Log>> { logs ->
                    if (logsList.size > 0) {
                        logsList.clear()
                    }
                    if (logs != null) {
                        logsList.addAll(logs)
                        logsList.reverse()
                    }

                   tv_log_head.text = "  time_passed>30K = invalid"

                    logAdapter.notifyDataSetChanged()
                })

    }


    override fun onClick(v: View?) {
        val data = "{}"

        when(v!!.id){
            R.id.btn_log_to_status->{
                v.findNavController().navigate(R.id.action_logFragment_to_statusFragment)
            }


            R.id.restart_korti -> {sendRestartKorti(data)
                Toast.makeText(this.context,"restart sent", Toast.LENGTH_SHORT).show()
//                if (this.isRestartEnabled){
//                    v.background = R.drawable.ic_power_settings_new_chosen_24dp.toDrawable()
//                    this.isRestartEnabled = false
//                } else{
//                    v.background = R.drawable.ic_power_settings_new_black_24dp.toDrawable()
//                    this.isRestartEnabled = true
//                }
            }



            R.id.btn_log_to_dash->{
                v.findNavController().navigate(R.id.action_logFragment_to_dashFragment)
            }


            else->{
                Toast.makeText(this.context,"try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(position: Int, item: Log) {
        Toast.makeText(this.context, "$position chosen", Toast.LENGTH_SHORT).show()    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

}