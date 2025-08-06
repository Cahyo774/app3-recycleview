package com.smkth.app3_recycleview.utils

import com.smkth.app3_recycleview.model.Student

object DummyData {
    fun getStudentList(): List<Student> {
        return listOf(
            Student("Faradhina", "100001", "XII RPL 1"),
            Student("Budi Wiwowi", "100002", "XII RPL 1"),
            Student("Brem", "100003", "XII RPL 1"),
            Student("Cinta", "100004", "XII RPL 1"),
            Student("Andi", "100005", "XII RPL 2"),
            Student("Okta", "100006", "XII RPL 2"),
            Student("Soli", "100007", "XII RPL 2"),
            Student("Tari", "100008", "XII RPL 3"),
            Student("Rina", "100009", "XII RPL 3"),
            Student("Zul", "100010", "XII RPL 3")
        )
    }
}
