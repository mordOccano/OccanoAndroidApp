package com.e.occanotestsidep.ui.main.log

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.e.occanosidetest.models.Log
import com.e.occanosidetest.utils.Run
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.persistence.LogRepository
import com.e.occanotestsidep.utils.Single
import kotlinx.android.synthetic.main.fragment_log.*
import org.json.JSONArray
import org.json.JSONObject


class LogFragment : Fragment(), View.OnClickListener,LogAdapter.Interaction{

    private val TAG: String = "log_fragment"
    var logsList = ArrayList<Log>()
    lateinit var logAdapter: LogAdapter
    private var ip: String? = null
    var mLogRepository: LogRepository? = null
    var irValid:Boolean = false
    var dbValid:Int = 0
    var timePassedJsObj:Int = 0
    lateinit var viewc :View
    lateinit var itemIr:JSONArray

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

        initRv()
        retriveLogs()
        logAdapter.submitList(logsList)
        getValidDbFromApi()
//        getValidDashFromApi()

//        repeatGetDashData()
    }

    private fun getValidDbFromApi() {
        getValidDb()
        repeatGetDbData()
    }

    private fun repeatGetDbData() {
//        Run.after(300000) {getValidDb()}
        Run.after(5000) {getValidDb()}
    }

    private fun getValidDashFromApi() {
        getValidDashboard()
        repeatGetDashData()
    }

    private fun repeatGetDashData() {
        Run.after(5000) {getValidDashFromApi()}
//        Run.after(300000) {getValidDashFromApi()}
    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.btn_log_to_dash).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_log_to_status).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_log_current).setOnClickListener(this)
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

    fun getValidDashboard(){

        val url  = "http://$ip/report/"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

         timePassedJsObj = response.getInt("time_passed")

                setValidDasboardUi(timePassedJsObj,viewc)

            },Response.ErrorListener { error ->

                if(error != null && error.message != null)
                {
                    Toast.makeText(context,"הי!! יש בעיה. צור קשר - מרדכי",Toast.LENGTH_SHORT).show()

                }
            })
        Single.getInstance(this.context).addToRequestQueue(jsonObjectRequest)
    }



    fun getValidDb(){
    val url  = "http://$ip/records/"

    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        Response.Listener { response ->
            android.util.Log.d(TAG,"Response: %s".format(response.toString()))
//            Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show()

            if(response.getInt("amount")>0){
                itemIr = response.getJSONArray("features")

                irValid = false
                for (i in 0 until itemIr.length()) {
                    if ("ir".equals(itemIr.get(i))) {
                        irValid = true
                    }
                }
                setValidIrUi(irValid,viewc)


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

            else{
                Toast.makeText(context,"נסה שוב עוד מעט".toString(),Toast.LENGTH_SHORT).show()
            }





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

    private fun setValidDbUi(dbValid: Int,view: View) {
        if (dbValid<900000){
            view.findViewById<ImageView>(R.id.iv_valid_db).setImageResource(R.drawable.ic_check_black_24dp)
        }
        else{
            view.findViewById<ImageView>(R.id.iv_valid_db).setImageResource(R.drawable.ic_close_black_24dp)
        }    }

    private fun setValidIrUi(irValid: Boolean,view: View) {
        if (irValid){
            view.findViewById<ImageView>(R.id.iv_valid_ir).setImageResource(R.drawable.ic_check_black_24dp)
        }
        else{
            view.findViewById<ImageView>(R.id.iv_valid_ir).setImageResource(R.drawable.ic_close_black_24dp)
        }
    }

    private fun setValidDasboardUi(dashValid: Int,view: View) {
        if (dashValid<30000){
            view.findViewById<ImageView>(R.id.iv_valid_dashboard).setImageResource(R.drawable.ic_check_black_24dp)
        }
        else{
            view.findViewById<ImageView>(R.id.iv_valid_dashboard).setImageResource(R.drawable.ic_close_black_24dp)
        }
    }



    private fun retriveLogs() {

        this.mLogRepository!!.retriveLogTask()
            .observe(this,
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
        when(v!!.id){
            R.id.btn_log_to_status->{
                v.findNavController().navigate(R.id.action_logFragment_to_statusFragment)
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