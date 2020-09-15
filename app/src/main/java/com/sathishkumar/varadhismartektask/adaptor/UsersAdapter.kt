package com.sathishkumar.varadhismartektask.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sathishkumar.varadhismartektask.interfaces.ClickItemListener
import com.sathishkumar.varadhismartektask.R
import com.sathishkumar.varadhismartektask.model.Users
import kotlinx.android.synthetic.main.posts_item.view.*

class UsersAdapter(val context: Context, val userList : List<Users>, val clickItemListener: ClickItemListener) : RecyclerView.Adapter<UsersAdapter.NameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.posts_item, parent, false)
        return NameViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val user = userList[position]    // getting each Post based on the position
        holder.itemView.tv_institute_name.text = context.getString(R.string.STR_INSTITUTE_NAME, user.Institution_Name)
        holder.itemView.tv_poc_name.text = context.getString(R.string.STR_POC_NAME, user.POC_Name)
        holder.itemView.tv_employee_id.text = context.getString(R.string.STR_EMPLOYEE_ID, user.marketing_user_employee_id)

        holder.itemView.setOnClickListener{
            clickItemListener.onClick(0,holder.adapterPosition)
        }

    }

    // View Holder Class
    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}