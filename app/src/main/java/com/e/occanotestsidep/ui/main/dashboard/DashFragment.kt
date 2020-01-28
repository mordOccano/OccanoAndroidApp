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
import com.github.anastr.speedviewlib.AwesomeSpeedometer
import com.github.anastr.speedviewlib.Gauge
import com.github.anastr.speedviewlib.ImageSpeedometer
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

    var torqueGauge: AwesomeSpeedometer? = null
    var bmepGauge: AwesomeSpeedometer? = null
    var scaveGauge: AwesomeSpeedometer? = null
    var imepGauge: AwesomeSpeedometer? = null
    var breakGauge: AwesomeSpeedometer? = null
    var injecGauge: AwesomeSpeedometer? = null
    var rpmGauge: AwesomeSpeedometer? = null
    var compPresGauge: AwesomeSpeedometer? = null
    var exsahustGauge: AwesomeSpeedometer? = null
    var fuelGauge: AwesomeSpeedometer? = null
    var firingGauge: AwesomeSpeedometer? = null
    var loadGauge: AwesomeSpeedometer? = null

    var btnDashCurr:ImageButton? = null
    var btnDasToLog:ImageButton? = null
    var btnToStatus:ImageButton? = null

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

        setPointer(view)
        setListeners()
        getDataFromApi()
//        forceCalibrationUi(view,gaugeOfCalibValue)
    }

    private fun setPointer(v:View) {
        torqueGauge =  AwesomeSpeedometer(context!!)
        bmepGauge = AwesomeSpeedometer(context!!)
        scaveGauge = AwesomeSpeedometer(context!!)
        imepGauge = AwesomeSpeedometer(context!!)
        breakGauge = AwesomeSpeedometer(context!!)
        injecGauge = AwesomeSpeedometer(context!!)
        rpmGauge = AwesomeSpeedometer(context!!)
        compPresGauge = AwesomeSpeedometer(context!!)
        exsahustGauge = AwesomeSpeedometer(context!!)
        fuelGauge = AwesomeSpeedometer(context!!)
        firingGauge = AwesomeSpeedometer(context!!)
        loadGauge = AwesomeSpeedometer(context!!)

        torqueGauge = v.findViewById(R.id.torque_gauge)
        bmepGauge = v.findViewById(R.id.bmep_gauge)
        breakGauge = v.findViewById(R.id.break_power_gauge)
        compPresGauge = v.findViewById(R.id.comp_pres_gauge)
        rpmGauge = v.findViewById(R.id.engine_speed_gauge)
        exsahustGauge = v.findViewById(R.id.exhaust_gauge)
        firingGauge = v.findViewById(R.id.firing_pres_gauge)
        fuelGauge = v.findViewById(R.id.fuel_gauge)
        imepGauge = v.findViewById(R.id.imep_gauge)
        injecGauge = v.findViewById(R.id.injec_gauge)
        loadGauge = v.findViewById(R.id.load_gauge)
        scaveGauge = v.findViewById(R.id.scave_gauge)

        btnDashCurr = v.findViewById(R.id.btn_dash_current)
        btnDasToLog = v.findViewById(R.id.btn_dash_to_log)
        btnToStatus=  v.findViewById(R.id.btn_dash_to_status)
    }

    private fun forceCalibrationUi(view: View, value:Float) {
        when (this.gaugeForCalibration.name){
            "torque_gauge"-> {
               torqueGauge!!.speedTo(value)
                return
            }
            "load_gauge"-> {
                loadGauge!!.speedTo(value)
                return
            }
            "firing_pres_gauge"-> {
                firingGauge!!.speedTo(value)
                return

            }
            "bmep_gauge"-> {
                bmepGauge!!.speedTo(value)
                return

            }
            "break_power_gauge"-> {
                breakGauge!!.speedTo(value)
                return

            }
            "comp_pres_gauge"-> {
                compPresGauge!!.speedTo(value)
                return

            }
            "engine_speed_gauge"-> {
                rpmGauge!!.speedTo(value)
                return


            }
            "exhaust_gauge"-> {
                exsahustGauge!!.speedTo(value)
                return


            }
            "fuel_gauge"-> {
                fuelGauge!!.speedTo(value)
                return


            }
            "imep_gauge"-> {
                imepGauge!!.speedTo(value)
                return


                //TODO: update the unit
            }
            "injec_gauge"-> {
                injecGauge!!.speedTo(value)
                return


            }
            "scave_gauge"-> {
                scaveGauge!!.speedTo(value)
                return


            }
            else -> {
                scaveGauge!!.speedTo(value)
                return


            }


        }

    }


    private fun setListeners(){
        torqueGauge!!.setOnClickListener(this)
        injecGauge!!.setOnClickListener(this)
        bmepGauge!!.setOnClickListener(this)
        breakGauge!!.setOnClickListener(this)
        compPresGauge!!.setOnClickListener(this)
        rpmGauge!!.setOnClickListener(this)
        exsahustGauge!!.setOnClickListener(this)
        firingGauge!!.setOnClickListener(this)
        fuelGauge!!.setOnClickListener(this)
        imepGauge!!.setOnClickListener(this)
        loadGauge!!.setOnClickListener(this)
        scaveGauge!!.setOnClickListener(this)

        btnDashCurr!!.setOnClickListener(this)
        btnDasToLog!!.setOnClickListener(this)
        btnToStatus!!.setOnClickListener(this)

        setMaxGauges()

    }

    private fun getDataFromApi() {
        newJsonParse()
        repeatGetData()
    }

    private fun repeatGetData() {
        Run.after(7000,{getDataFromApi()})
    }

    private fun setMaxGauges() {
       torqueGauge!!.setMaxSpeed(max_torque_gauge)
        bmepGauge!!.setMaxSpeed(max_bmep_gauge)
        breakGauge!!.setMaxSpeed(max_break_power_gauge)
        compPresGauge!!.setMaxSpeed(max_comp_pres_gauge)
        rpmGauge!!.setMaxSpeed(max_engine_speed_gauge)
        exsahustGauge!!.setMaxSpeed(max_exhaust_gauge)
        firingGauge!!.setMaxSpeed(max_firing_pres_gauge)
        fuelGauge!!.setMaxSpeed(max_fuel_gauge)
        imepGauge!!.setMaxSpeed(max_imep_gauge)
        injecGauge!!.setMaxSpeed(max_injec_gauge)
        loadGauge!!.setMaxSpeed(max_load_gauge)
        scaveGauge!!.setMaxSpeed(max_scave_gauge)

        setTicks()
    }

    private fun setTicks() {
        torqueGauge!!.tickNumber=9
        bmepGauge!!.tickNumber=9
        breakGauge!!.tickNumber=9
        compPresGauge!!.tickNumber=9
        rpmGauge!!.tickNumber=9
        exsahustGauge!!.tickNumber=9
        firingGauge!!.tickNumber=9
        fuelGauge!!.tickNumber=9
        imepGauge!!.tickNumber=9
        injecGauge!!.tickNumber=9
        loadGauge!!.tickNumber=9
        scaveGauge!!.tickNumber=9
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


                setGauges()
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

    fun setGauges() {
        torqueGauge?.speedTo(torque_engine!!)
        bmepGauge?.speedTo(bmep!!)
        breakGauge?.speedTo(break_power!!)
        compPresGauge?.speedTo(compression_pressure!!)
        rpmGauge?.speedTo(engine_speed!!)
        exsahustGauge?.speedTo(exhaust_temperature!!)
        firingGauge?.speedTo(firing_pressure!!)
        fuelGauge?.speedTo(fuel_flow_rate!!)
        imepGauge?.speedTo(imep!!)
        injecGauge?.speedTo(injection_timing!!)
        loadGauge?.speedTo(load!!)
        scaveGauge?.speedTo(scavenging_pressure!!)
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



