package com.smkth.app3_recycleview.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.smkth.app3_recycleview.R
import com.smkth.app3_recycleview.model.Student
import java.util.*

class StudentAdapter(
    private val context: Context,
    private val studentList: MutableList<Student>
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>(), Filterable {

    private var filteredList: MutableList<Student> = studentList.toMutableList()

    fun removeItem(position: Int) {
        val student = filteredList[position]
        studentList.remove(student)
        filteredList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editItem(position: Int, updatedStudent: Student) {
        val student = filteredList[position]
        val indexInOriginal = studentList.indexOf(student)
        if (indexInOriginal != -1) {
            studentList[indexInOriginal] = updatedStudent
        }
        filteredList[position] = updatedStudent
        notifyItemChanged(position)
    }

    fun refreshData() {
        filteredList = studentList.toMutableList()
        notifyDataSetChanged()
    }

    private fun showEditDialog(position: Int) {
        val student = filteredList[position]
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_student, null)
        val edtNama = dialogView.findViewById<EditText>(R.id.edtNama)
        val edtNis = dialogView.findViewById<EditText>(R.id.edtNis)
        val edtKelas = dialogView.findViewById<EditText>(R.id.edtKelas)

        edtNama.setText(student.nama)
        edtNis.setText(student.nis)
        edtKelas.setText(student.kelas)

        AlertDialog.Builder(context)
            .setTitle("Edit Data Siswa")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val updatedStudent = Student(
                    edtNama.text.toString(),
                    edtNis.text.toString(),
                    edtKelas.text.toString()
                )
                editItem(position, updatedStudent)
                Toast.makeText(context, "Data siswa berhasil diupdate", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvNama)
        val tvNis: TextView = itemView.findViewById(R.id.tvNis)
        val tvKelas: TextView = itemView.findViewById(R.id.tvKelas)
        val btnEdit: View = itemView.findViewById(R.id.btnEdit)
        val btnHapus: View = itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = filteredList[position]
        holder.tvName.text = student.nama
        holder.tvNis.text = "NIS: ${student.nis}"
        holder.tvKelas.text = "Kelas: ${student.kelas}"

        holder.btnEdit.setOnClickListener {
            showEditDialog(holder.adapterPosition)
        }

        holder.btnHapus.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Hapus Data")
                .setMessage("Yakin ingin menghapus ${student.nama}?")
                .setPositiveButton("Ya") { _, _ ->
                    removeItem(holder.adapterPosition)
                    Toast.makeText(context, "${student.nama} dihapus", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val keyword = query?.toString()?.lowercase(Locale.ROOT)?.trim() ?: ""

                val resultList = if (keyword.isEmpty()) {
                    studentList
                } else {
                    studentList.filter {
                        it.nama.lowercase(Locale.ROOT).contains(keyword) ||
                                it.nis.lowercase(Locale.ROOT).contains(keyword) ||
                                it.kelas.lowercase(Locale.ROOT).contains(keyword)
                    }
                }

                val results = FilterResults()
                results.values = resultList.toMutableList()
                return results
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Student>
                notifyDataSetChanged()
            }
        }
    }
}
