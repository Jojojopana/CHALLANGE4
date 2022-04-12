package com.binar.challange_part4.Fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challange_part4.Student
import com.binar.challange_part4.StudentAdapter
import com.binar.challange_part4.databinding.ActivityAddBinding
import com.binar.challange_part4.databinding.ActivityEditBinding
import com.binar.challange_part4.databinding.FragmentHomeBinding
import com.binar.challange_part4.databinding.StudentItemBinding
import com.binar.challange_part4.room.StudentDatabase
import kotlinx.coroutines.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var binding2: ActivityAddBinding
    private var mDB: StudentDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val view = binding.root
        return (view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDB = StudentDatabase.getInstance(requireContext())
        

        val layoutmanager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.Recycler.layoutManager = layoutmanager
        fetchData()

        binding.fabAdd.setOnClickListener {
            DialogAdd()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(): Job = lifecycleScope.launch(Dispatchers.IO) {
         val listStudent = mDB?.studentDao()?.getAllStudent()

        activity?.runOnUiThread {
            listStudent?.let {
                val adapter = StudentAdapter(it,
                    edit = { data ->
                        val dialogBinding = ActivityEditBinding.inflate(LayoutInflater.from(requireContext()))
                        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        dialogBuilder.setView(dialogBinding.root)
                        val dialog = dialogBuilder.create()
                        dialog.setCancelable(false)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialogBinding.etEmailStudent.setText("${data.email}")
                        dialogBinding.etNamaStudent.setText("${data.nama}")
                        dialogBinding.btnSave.setOnClickListener{
                            val mDB = StudentDatabase.getInstance(requireContext())
                            data.email = dialogBinding.etEmailStudent.text.toString()
                            data.nama = dialogBinding.etNamaStudent.text.toString()
                            lifecycleScope.launch(Dispatchers.IO){
                                val result = mDB?.studentDao()?.updateStudent(data)
                                runBlocking(Dispatchers.Main){
                                    if(result != 0){
                                        Toast.makeText(requireContext(),"${data.email} Berhasil di update!", Toast.LENGTH_LONG).show()
                                        fetchData()
                                        dialog.dismiss()
                                    } else {
                                        Toast.makeText(requireContext(), "${data.email} Gagal di update", Toast.LENGTH_LONG).show()
                                        fetchData()
                                        dialog.dismiss()
                                    }
                                }
                            }
                        }
                        dialog.show()

                    })
                binding.Recycler.adapter = adapter
            }
        }
     }


    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
    }

     private fun DialogAdd() {
         binding2 = ActivityAddBinding.inflate(layoutInflater)
         // Inflate the layout for this fragment
         val view = binding2.root
         val dialogBuilder = AlertDialog.Builder(requireContext())
         dialogBuilder.setView(view)
         val dialog = dialogBuilder.create()
         dialog.setCancelable(false)

         dialog.show()

         binding2.btnSave.setOnClickListener {

             val objectStudent = Student(
                 null,
                 binding2.etNamaStudent.text.toString(),
                 binding2.etEmailStudent.text.toString()
             )

             lifecycleScope.launch(Dispatchers.IO) {
                 val result = mDB?.studentDao()?.insertStudent(objectStudent)
                 activity?.runOnUiThread {
                     if (result != 0.toLong()) {
                         //sukses
                         Toast.makeText(
                             requireContext(), "Sukses menambahkan ${objectStudent.nama}",
                             Toast.LENGTH_LONG
                         ).show()
                         fetchData()
                     } else {
                         //gagal
                         Toast.makeText(
                             requireContext(), "Gagal menambahkan ${objectStudent.nama}",
                             Toast.LENGTH_LONG
                         ).show()
                     }
                 }
             }
             dialog.dismiss()
         }
     }
}
