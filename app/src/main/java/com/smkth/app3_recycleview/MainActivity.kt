package com.smkth.app3_recycleview

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smkth.app3_recycleview.adapter.StudentAdapter
import com.smkth.app3_recycleview.model.Student
import com.smkth.app3_recycleview.utils.DummyData

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = DummyData.getStudentList().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = StudentAdapter(this, studentList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        btnTambah.setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun showAddStudentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_student, null)
        val edtNama = dialogView.findViewById<EditText>(R.id.edtNama)
        val edtNis = dialogView.findViewById<EditText>(R.id.edtNis)
        val edtKelas = dialogView.findViewById<EditText>(R.id.edtKelas)

        AlertDialog.Builder(this)
            .setTitle("Tambah Siswa Baru")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val student = Student(
                    edtNama.text.toString(),
                    edtNis.text.toString(),
                    edtKelas.text.toString()
                )
                studentList.add(student)
                adapter.refreshData() // ⬅️ agar tampil dan sinkron dengan search
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
