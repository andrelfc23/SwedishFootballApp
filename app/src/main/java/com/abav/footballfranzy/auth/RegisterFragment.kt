package com.abav.footballfranzy.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.abav.footballfranzy.R
import com.abav.footballfranzy.auth.AuthActivity
import com.abav.footballfranzy.databinding.FragmentRegisterBinding
import com.abav.footballfranzy.model.UserRegisterModel

class RegisterFragment : Fragment() {
    // Finding instead of find view by id,
    private var _binding: FragmentRegisterBinding?=null
    private val binding get()=  _binding
    private lateinit var auth: FirebaseAuth
    private var uID:String?= null
    private var name:String?= null
    private var phone:String?= null
    private var email:String?= null
    private var password:String?= null
    private lateinit var authRegisterUser: UserRegisterModel
    // When click on register button we display loader to user till it suceed
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        initViews()
    }

    private fun initClick() {

        binding?.registerBTN?.setOnClickListener {
            // signup button
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


    private fun registerCustomer(email: String, password: String) {
        auth.createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener{
                if (it.isSuccessful) {
                    // dismiss the dialog here
                    progressDialog.dismiss()

                    uID=auth.currentUser?.uid
                    authRegisterUser =
                        UserRegisterModel(uID, name, phone, this.email, this.password)

                    saveCustomerInfo(
                        authRegisterUser
                    )
                }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }

    private fun saveCustomerInfo(authRegisterUser: UserRegisterModel) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .set(authRegisterUser)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "Successfully signed up!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finishAffinity()
                }
            }
            .addOnFailureListener { exception ->
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
            }
    }


    private fun validations() {
        name = binding?.nameET?.text.toString()
        phone = binding?.phoneET?.text.toString()
        email = binding?.emailET?.text.toString()
        password = binding?.passwordET?.text.toString()
        if (name!!.isEmpty()) {
            binding?.nameET?.requestFocus()
            binding?.nameET?.error = getString(R.string.enter_name)
        } else if (phone!!.isEmpty()) {
            binding?.phoneET?.requestFocus()
            binding?.phoneET?.error = getString(R.string.enter_phone)
        }
        else if (email!!.isEmpty()) {
            binding?.emailET?.requestFocus()
            binding?.emailET?.error = getString(R.string.enter_email)
        } else if (password!!.isEmpty()) {
            binding?.passwordET?.requestFocus()
            binding?.passwordET?.error = getString(R.string.enter_password)
        }  else {
            progressDialog.show()

            registerCustomer(email!!, password!!)
        }

    }


}
