package com.sathishkumar.varadhismartektask.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sathishkumar.varadhismartektask.interfaces.ClickItemListener
import com.sathishkumar.varadhismartektask.R
import com.sathishkumar.varadhismartektask.adaptor.UsersAdapter
import com.sathishkumar.varadhismartektask.model.ResponseData
import com.sathishkumar.varadhismartektask.model.Users
import com.sathishkumar.varadhismartektask.netwrok.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), ClickItemListener {
    private lateinit var userList: List<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Inflating the layout
        loader.visibility = View.VISIBLE
        getUsersData()
    }

    // setting data to adapter and showing the count and total time
    fun showData(userList: List<Users>) {
        loader.visibility = View.GONE

        rv_list.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_list.adapter = UsersAdapter(this, userList, this) // passing data to adapter
    }

    fun getUsersData() { // method to get Users data
        //userList = mutableListOf()
        val userApi = RetrofitClient.userAPI

        val call =
            userApi.get_booking_appointment_list("VI020PE0016", "All", "Employee", "2020-07-25")

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    loader.visibility = View.GONE
                    if (response.body()?.status_code == 200) {
                        val responseData = response.body()
                        if (responseData?.unassigned != null) {
                            userList = responseData.unassigned
                            showData(userList)
                        } else tv_no_data.visibility = View.VISIBLE


                    } else tv_no_data.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {

            }
        })
    }

    override fun onClick(section: Int, position: Int) {
        if (section == 0) {
            val user = userList[position]

            val address = user.Address_Line_1+","+user.Address_Line_2+","+user.Land_Mark+","+user.City+","+user.State+","+user.PIN.toString()
            intent = Intent(this, MapActivity::class.java)
            intent.putExtra("address",address)
            startActivity(intent)
        }
    }

}
