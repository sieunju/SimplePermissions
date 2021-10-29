package com.hmju.example

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hmju.permissions.SimplePermissions
import java.util.*

/**
 * Description :
 *
 * Created by hmju on 2021-10-27
 */
class ExampleFragment : Fragment(R.layout.fragment_example) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button1).setOnClickListener {
            SimplePermissions(requireContext())
                .requestPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).build { isAllGranted, negativePermissions ->
                    startActivity(Intent(requireContext(),ExampleActivity::class.java))
                    requireActivity().finish()
                }
        }
        view.findViewById<Button>(R.id.button3).setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                add(R.id.childFragment, ExampleFragment())
                addToBackStack(null)
                commit()
            }
        }
    }
}