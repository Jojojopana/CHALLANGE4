package com.binar.challange_part4.Fragment

import android.content.Context
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challange_part4.Activities.MainActivity
import com.binar.challange_part4.R
import com.binar.challange_part4.User
import com.binar.challange_part4.databinding.FragmentHomeBinding
import com.binar.challange_part4.databinding.FragmentLoginBinding
import com.binar.challange_part4.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private var mDB: UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = binding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDB = UserDatabase.getInstance(requireContext())

        val sharedPreferences = requireContext()
            .getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)


        binding.toregister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tombolLogin.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val meriksa = User(null, username, password)

            lifecycleScope.launch(Dispatchers.IO) {
                val login = mDB?.userDao()?.login(username, password)
                activity?.runOnUiThread {
                    if (login == null) {
                        Toast.makeText(
                            context,
                            "Username atau Password Anda Salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }

                }
            }

        }

    }
}