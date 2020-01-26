package com.e.occanotestsidep.ui.main.dashboard

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.occanosidetest.models.GaugeForCalibration
import com.e.occanosidetest.models.Log
import com.e.occanosidetest.utils.Run
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanosidetest.utils.StaticAddress.Companion.max_bmep_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_break_power_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_comp_pres_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_engine_speed_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_exhaust_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_firing_pres_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_fuel_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_imep_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_injec_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_load_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_scave_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_torque_gauge
import com.e.occanotestsidep.R
import com.e.occanotestsidep.persistence.LogRepository
import com.e.occanotestsidep.utils.Single
import com.e.occanotestsidep.utils.Utility
import com.github.anastr.speedviewlib.Gauge
import com.github.anastr.speedviewlib.util.OnPrintTickLabel
import kotlinx.android.synthetic.main.fragment_calibration_fragment_z.*
import kotlinx.android.synthetic.main.fragment_launcher.*
import java.util.*


class DashFragment : Fragment() ,View.OnClickListener{

    private var ip: String? = null
    lateinit var gaugeForCalibration: GaugeForCalibration
    lateinit var stringRequest: StringRequest
    lateinit var requestQueue: RequestQueue
    var engine_speed: Float?=0.0f
    var exhaust_temperature: Float?=0.0f
    var load: Float?=0.0f
    var firing_pressure: Float?=0.0f
    var scavenging_pressure: Float?=0.0f
    var compression_pressure: Float?=0.0f
    var break_power: Float?=0.0f
    var imep: Float?=0.0f
    var torque_engine: Float?=0.0f
    var bmep: Float?=0.0f
    var injection_timing: Float?=0.0f
    var fuel_flow_rate: Float?=0.0f

    lateinit var torqueGauge: Gauge

    lateinit var mLogRepository: LogRepository
    var toastCounter:Boolean = false

    var time_passed:Long? = 0

    lateinit var viewc :View

    private val TAG = "DashboardFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLogRepository = LogRepository(context)

//        gaugeForCalibration = arguments?.getParcelable("gauge_from_manual")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewc = inflater.inflate(R.layout.fragment_dash, container, false)
        return viewc
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val gaugeOfCalibName = this.gaugeForCalibration.name;
//        val gaugeOfCalibValue = this.gaugeForCalibration.value;
        getAddress()

        requestQueue = Volley.newRequestQueue(context)

        setListeners(view)
        getDataFromApi()
//        forceCalibrationUi(view,gaugeOfCalibValue)
    }

    private fun forceCalibrationUi(view: View, value:Float) {
        when (this.gaugeForCalibration.name){
            "torque_gauge"-> {
               view.findViewById<Gauge>(R.id.torque_gauge).speedTo(value)
                return
            }
            "load_gauge"-> {
                view.findViewById<Gauge>(R.id.load_gauge).speedTo(value)
                return
            }
            "firing_pres_gauge"-> {
                view.findViewById<Gauge>(R.id.firing_pres_gauge).speedTo(value)
                return

            }
            "bmep_gauge"-> {
                view.findViewById<Gauge>(R.id.bmep_gauge).speedTo(value)
                return

            }
            "break_power_gauge"-> {
                view.findViewById<Gauge>(R.id.break_power_gauge).speedTo(value)
                return

            }
            "comp_pres_gauge"-> {
                view.findViewById<Gauge>(R.id.comp_pres_gauge).speedTo(value)
                return

            }
            "engine_speed_gauge"-> {
                view.findViewById<Gauge>(R.id.engine_speed_gauge).speedTo(value)
                return


            }
            "exhaust_gauge"-> {
                view.findViewById<Gauge>(R.id.exhaust_gauge).speedTo(value)
                return


            }
            "fuel_gauge"-> {
                view.findViewById<Gauge>(R.id.fuel_gauge).speedTo(value)
                return


            }
            "imep_gauge"-> {
                view.findViewById<Gauge>(R.id.imep_gauge).speedTo(value)
                return


                //TODO: update the unit
            }
            "injec_gauge"-> {
                view.findViewById<Gauge>(R.id.injec_gauge).speedTo(value)
                return


            }
            "scave_gauge"-> {
                view.findViewById<Gauge>(R.id.scave_gauge).speedTo(value)
                return


            }
            else -> {
                view.findViewById<Gauge>(R.id.scave_gauge).speedTo(value)
                return


            }


        }

    }


    private fun setListeners( v :View){
        v.findViewById<Gauge>(R.id.torque_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.injec_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.bmep_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.break_power_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.comp_pres_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.engine_speed_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.exhaust_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.firing_pres_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.fuel_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.imep_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.load_gauge).setOnClickListener(this)
        v.findViewById<Gauge>(R.id.scave_gauge).setOnClickListener(this)

        setMaxGauges(v)

        v.findViewById<ImageButton>(R.id.btn_dash_current).setOnClickListener(this)
        v.findViewById<ImageButton>(R.id.btn_dash_to_log).setOnClickListener(this)
        v.findViewById<ImageButton>(R.id.btn_dash_to_status).setOnClickListener(this)
    }

    private fun getDataFromApi() {
        newJsonParse()
        repeatGetData()
    }

    private fun repeatGetData() {
        Run.after(7000,{getDataFromApi()})
    }

    private fun setMaxGauges(v:View) {
        torqueGauge = v.findViewById(R.id.torque_gauge)
        torqueGauge.setMaxSpeed(max_torque_gauge)
        torqueGauge.setStartDegree(0)

//        torqueGauge.onPrintTickLabel = object : OnPrintTickLabel {
//            fun getTickLabel(tickPosition: Int, tick: Int): CharSequence? {
//                return if (tick >= 1000) String.format(Locale.getDefault(), "%.1f Km", tick / 1000f) else null
//                // null means draw default tick label.
//                // also you can return SpannableString to change color, textSize, lines...
//            }
//        }

        v.findViewById<Gauge>(R.id.bmep_gauge).setMaxSpeed(max_bmep_gauge)
        v.findViewById<Gauge>(R.id.break_power_gauge).setMaxSpeed(max_break_power_gauge)
        v.findViewById<Gauge>(R.id.comp_pres_gauge).setMaxSpeed(max_comp_pres_gauge)
        v.findViewById<Gauge>(R.id.engine_speed_gauge).setMaxSpeed(max_engine_speed_gauge)
        v.findViewById<Gauge>(R.id.exhaust_gauge).setMaxSpeed(max_exhaust_gauge)
        v.findViewById<Gauge>(R.id.firing_pres_gauge).setMaxSpeed(max_firing_pres_gauge)
        v.findViewById<Gauge>(R.id.fuel_gauge).setMaxSpeed(max_fuel_gauge)
        v.findViewById<Gauge>(R.id.imep_gauge).setMaxSpeed(max_imep_gauge)
        v.findViewById<Gauge>(R.id.injec_gauge).setMaxSpeed(max_injec_gauge)
        v.findViewById<Gauge>(R.id.load_gauge).setMaxSpeed(max_load_gauge)
        v.findViewById<Gauge>(R.id.scave_gauge).setMaxSpeed(max_scave_gauge)
    }

    private fun newJsonParse() {

        val url  = "http://"+ip+"/report/"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                android.util.Log.d(TAG,"Response: %s".format(response.toString()))

//                 Toast.makeText(context,"Response: %s".format(response.toString()),Toast.LENGTH_SHORT).show()


                val item = response.getJSONObject("analysis")
                engine_speed = item.getDouble("engine_speed").toFloat()
                exhaust_temperature = item.getDouble("exhaust_temperature").toFloat()
                load = item.getDouble("load").toFloat()
                firing_pressure = item.getDouble("firing_pressure").toFloat()
                scavenging_pressure = item.getDouble("scavenging_pressure").toFloat()
                compression_pressure = item.getDouble("compression_pressure").toFloat()
                break_power = item.getDouble("break_power").toFloat()
                imep = item.getDouble("imep").toFloat()
                torque_engine = item.getDouble("Torque_engine").toFloat()/1000
                bmep = item.getDouble("bmep").toFloat()
                injection_timing = item.getDouble("injection_timing").toFloat()
                fuel_flow_rate = item.getDouble("fuel_flow_rate").toFloat()



                val mLog = Log()
                mLog.content = "Response: %s".format(response.toString())+Utility.getCurrentTimeStamp()
                saveToLog(mLog)


                setGauges(viewc)
            },
            Response.ErrorListener { error ->

                if(error != null && error.message != null)
                {

//                    Log.d(TAG, "onResponse: Faild beacause: %s".format(error.toString()))
//                    tv_dash_log.text = "onResponse: Faild beacause: %s".format(error.toString())
                    if (toastCounter == false){
                        if (context !=null){
                            Toast.makeText(context,"הי מתוק!! יש בעיה. עבור אל מסך הלוג ותיצור איתי קשר - מרדכי",Toast.LENGTH_SHORT).show()
                        }
                        toastCounter=true
                    }
                    val mLog = Log()
                    mLog.content = "Response: %s".format(error.toString())
                    saveToLog(mLog)

                }
//                else if (error.networkResponse.statusCode == 404){
////                    findNavController().navigate(R.id.action_dashFragment_to_launcherFragment)
//
//
////                    var timeErrorPassed = error.networkResponse.data
//                    Toast.makeText(this.context,"404",Toast.LENGTH_SHORT).show()
//
//                }
//                else if (error is TimeoutError ) {
//                    Toast.makeText(this.context,"please connect to corti",Toast.LENGTH_LONG).show()
////                    findNavController().navigate(R.id.action_dashFragment_to_launcherFragment)
//                }


//                Log.d(TAG, "onResponse: Faild beacause: %s".format(error.toString()))
//                tv_dash_log.text = "onResponse: Faild beacause: %s".format(error.toString())

            }

        )

// Access the RequestQueue through your singleton class.
        Single.getInstance(this.context).addToRequestQueue(jsonObjectRequest)

    }



    private fun saveToLog(log: Log) {
//        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//        with (sharedPref.edit()) {
//            putString("log", s)
//            commit()
//        }
            this.mLogRepository.insertLogTask(log)
//        Toast.makeText(context,"done",Toast.LENGTH_SHORT).show()

    }

//pause the request on pause the fragment
//    override fun onPause() {
//        super.onPause()
//        Single.getInstance().
//    }

    fun setGauges(v: View) {

        v.findViewById<Gauge>(R.id.torque_gauge).speedTo(torque_engine!!)
        v.findViewById<Gauge>(R.id.bmep_gauge).speedTo(bmep!!)
        v.findViewById<Gauge>(R.id.break_power_gauge).speedTo(break_power!!)
        v.findViewById<Gauge>(R.id.comp_pres_gauge).speedTo(compression_pressure!!)
        v.findViewById<Gauge>(R.id.engine_speed_gauge).speedTo(engine_speed!!)
        v.findViewById<Gauge>(R.id.exhaust_gauge).speedTo(exhaust_temperature!!)
        v.findViewById<Gauge>(R.id.firing_pres_gauge).speedTo(firing_pressure!!)
        v.findViewById<Gauge>(R.id.fuel_gauge).speedTo(fuel_flow_rate!!)
        v.findViewById<Gauge>(R.id.imep_gauge).speedTo(imep!!)
        v.findViewById<Gauge>(R.id.injec_gauge).speedTo(injection_timing!!)
        v.findViewById<Gauge>(R.id.load_gauge).speedTo(load!!)
        v.findViewById<Gauge>(R.id.scave_gauge).speedTo(scavenging_pressure!!)
    }


    override fun onClick(v: View?) {

        when(v!!.id){
            R.id.torque_gauge -> {
                if(!(TextUtils.isEmpty(torque_engine.toString()))){
                    var gauge = GaugeForCalibration()
                    gauge.name = "torque_gauge"
                    gauge.value = torque_engine!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.exhaust_gauge -> {
                if(!TextUtils.isEmpty(torque_engine.toString())){
                    var gauge = GaugeForCalibration()
                    gauge.name = "exhaust_gauge"
                    gauge.value = exhaust_temperature!!
                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.load_gauge -> {
                if(!TextUtils.isEmpty(load.toString())){
                    var gauge = GaugeForCalibration()

                         gauge.name = "load_gauge"
                         gauge.value = load!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.firing_pres_gauge -> {
                if(!TextUtils.isEmpty(firing_pressure.toString())){
                    var gauge = GaugeForCalibration()

                        gauge.name = "firing_pres_gauge"
                        gauge.value = firing_pressure!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.comp_pres_gauge -> {
                if(!TextUtils.isEmpty(compression_pressure.toString())){

                    var gauge = GaugeForCalibration()
                        gauge.name = "comp_pres_gauge"
                        gauge.value = compression_pressure!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.scave_gauge -> {
                if(!TextUtils.isEmpty(scavenging_pressure.toString())){

                    var gauge = GaugeForCalibration()
                    gauge.name = "scave_gauge"
                    gauge.value = scavenging_pressure!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.bmep_gauge -> {
                if(!TextUtils.isEmpty(bmep.toString())){
                    var gauge = GaugeForCalibration()
                    gauge.name = "bmep_gauge"
                    gauge.value = bmep!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.break_power_gauge -> {
                if(!TextUtils.isEmpty(break_power.toString())){
                    var gauge = GaugeForCalibration()
                    gauge.name = "break_power_gauge"
                    gauge.value = break_power!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.engine_speed_gauge -> {
                if(!TextUtils.isEmpty(engine_speed.toString())){
                    var gauge = GaugeForCalibration()
                    gauge.name = "engine_speed_gauge"
                    gauge.value = engine_speed!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.fuel_gauge -> {
                if(!TextUtils.isEmpty(fuel_flow_rate.toString())){
                    var gauge = GaugeForCalibration()
                    gauge.name = "fuel_gauge"
                    gauge.value = fuel_flow_rate!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.imep_gauge -> {
                if(!TextUtils.isEmpty(imep.toString())){
                    var gauge = GaugeForCalibration()
                    gauge.name = "imep_gauge"
                    gauge.value = imep!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ,bundle)
                }
                else{
                    Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.injec_gauge -> {
                if(!TextUtils.isEmpty(injection_timing.toString())) {
                    var gauge = GaugeForCalibration()
                    gauge.name ="injec_gauge"
                    gauge.value = injection_timing!!

                    val bundle = bundleOf("gauge_from_dashboard" to gauge)

                    findNavController().navigate(R.id.action_dashFragment_to_calibrationFragmentZ, bundle)
                }
                else{ Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_dash_to_status ->{
                findNavController().navigate(R.id.action_dashFragment_to_statusFragment)
            }
            R.id.btn_dash_to_log->{
                findNavController().navigate(R.id.action_dashFragment_to_logFragment)
            }
            else ->{
                Toast.makeText(this.context, "try Again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }
}



