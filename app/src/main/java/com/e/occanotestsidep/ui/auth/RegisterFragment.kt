package com.e.occanotestsidep.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanotestsidep.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        val ip = sharedPref.getString("ip", defaultValue)

            when(v!!.id){
                R.id.buttonRegister -> {
                    Toast.makeText(context,
                        ip, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
                }
            }
    }

//    fun getAddress(){
//        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//        val defaultValue = resources.getString(R.string.default_ip)
//        val ip = sharedPref.getString("ip", defaultValue)
//    }
}