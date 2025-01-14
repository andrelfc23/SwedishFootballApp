package com.abav.footballfranzy.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.google.firebase.auth.FirebaseAuth
import com.abav.footballfranzy.R
import com.abav.footballfranzy.databinding.FragmentLoginBinding
import com.abav.footballfranzy.home.MainHomeActivity

class LoginFragment : Fragment() {
    // Creating binding object, through this we can access all the ids/ all the views
    // of our xml file. Using this
    private var _binding: FragmentLoginBinding?= null
    private val binding get()=  _binding

    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        //replaced inflate with binding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view

    }
    // This function used to call all functions to initialize the views of the fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        initViews()

    }

    private fun loginUser(email:String, password : String) {
        auth.signInWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener {
                if(it.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                }
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "something wong!", Toast.LENGTH_SHORT).show()
            }
    }

    // ? means avoiding unexpected crashes, if we have any null value, avoids crashing
    private fun onClickListener() {
        binding?.registerBTN?.setOnClickListener {
            // navigate to register fragment

            // Will try to navigate from logion to register after register button has been clicked
            try {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

            }
            catch (e:Exception){
                e.printStackTrace()
                Log.d("Exceptionn", e.message.toString())

            }
        }
        // listern of login button
        binding?.loginBTN?.setOnClickListener {
            validations()
        }
    }
    private fun initViews() {
        auth = FirebaseAuth.getInstance()
        // intialize dialog
        progressDialog= ProgressDialog(requireActivity())
        progressDialog.setTitle(resources.getString(R.string.app_name))
        progressDialog.setMessage("please wait")
        progressDialog.setCanceledOnTouchOutside(false)
    }




    // Destroys our old fragment after we moved to a new fragment, increases app performance
    override fun onDestroy() {
        super.onDestroy()
        // When we move from login fragment to any other fragment, then it will
        // clear the view, it will clear the memory
        _binding = null
    }

    private fun validations() {
        val email = binding?.emailET?.text.toString()
        val password = binding?.passwordET?.text.toString()

        if (email.isEmpty()) {
            binding?.emailET?.requestFocus()
            binding?.emailET?.error = getString(R.string.enter_email)
        } else if (password.isEmpty()) {
            binding?.passwordET?.requestFocus()
            binding?.passwordET?.error = getString(R.string.enter_password)
        } else {
            // dismiss the dialog
            progressDialog.show()
            // function for login user
            loginUser(email, password)
        }
    }

    //naviagte to main home screen after login success
    private  fun navigateToMain(){
        val intent = Intent(requireActivity(), MainHomeActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }
}