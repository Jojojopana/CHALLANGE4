package com.binar.challange_part4.Fragment

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.binar.challange_part4.R
import com.binar.challange_part4.User
import com.binar.challange_part4.databinding.FragmentHomeBinding
import com.binar.challange_part4.databinding.FragmentRegisterBinding
import com.binar.challange_part4.room.StudentDatabase
import com.binar.challange_part4.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private var mDB: UserDatabase?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = binding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDB = UserDatabase.getInstance(requireContext())

       binding.tombolRegister.setOnClickListener{
           val username = binding.usernameRegister.text.toString()
           val password = binding.passwordRegister.text.toString()
           val password2 = binding.passwordRegister2.text.toString()
           val register = User(null, username, password)

           lifecycleScope.launch(Dispatchers.IO) {
               val result = mDB?.userDao()?.insert(register)
           }
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}