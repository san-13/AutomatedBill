package com.example.automatedbill.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.automatedbill.LedgerDirections
import com.example.automatedbill.R
import com.example.automatedbill.room.AllBills
import kotlinx.android.synthetic.main.all_bills_list.view.*

class AllBillsAdapter: RecyclerView.Adapter<AllBillsAdapter.MyViewHoler>() {

    private var allBills= emptyList<AllBills>()

    inner class MyViewHoler(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoler {
        return MyViewHoler(LayoutInflater.from(parent.context).inflate(R.layout.all_bills_list,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHoler, position: Int) {
            holder.itemView.billno.text= allBills[position].billno.toString()
            holder.itemView.bill_thumbnail.setImageBitmap(allBills[position].bill)
        holder.itemView.setOnClickListener {
            val action=LedgerDirections.actionLedgerToPrevBill(allBills[position].id)
                holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return allBills.size
    }

    fun setData(allBills:List<AllBills>){
        this.allBills = allBills
        notifyDataSetChanged()
    }
}