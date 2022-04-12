package com.binar.challange_part4

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.binar.challange_part4.Activities.MainActivity
import com.binar.challange_part4.databinding.StudentItemBinding
import com.binar.challange_part4.room.StudentDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class StudentAdapter(val listStudent: List<Student>, private val edit: (Student) -> Unit) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    class ViewHolder( val binding : StudentItemBinding): RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StudentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return listStudent.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvID.text = listStudent[position].id.toString()
        holder.binding.tvNama.text = listStudent[position].nama
        holder.binding.tvEmail.text = listStudent[position].email

        holder.binding.ivEdit.setOnClickListener{
            // Inflate the layout for this fragment
            edit.invoke(listStudent[position])
        }

        holder.binding.ivDelete.setOnClickListener{
            AlertDialog.Builder(it.context).setPositiveButton("Ya"){p0, p1 ->
                val mOb = StudentDatabase.getInstance(holder.itemView.context)

                 GlobalScope.async{
                    val result = mOb?.studentDao()?.deleteStudent(listStudent[position])

                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (result != 0) {
                            Toast.makeText(
                                it.context,
                                "Data ${listStudent[position].nama} berhasil dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                            (holder.itemView.context as MainActivity).recreate()
                        } else {
                            Toast.makeText(
                                it.context,
                                "Data ${listStudent[position].nama} gagal dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    (holder.itemView.context as MainActivity).recreate()
                }
            } .setNegativeButton("Tidak") { p0, p1 -> p0.dismiss() }
                .setMessage("Apakah Anda Yakin ingin menghapus data ${listStudent[position].nama}").setTitle("Konfirmasi Hapus").create().show()
        }
    }



}