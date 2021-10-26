package com.example.automatedbill

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.automatedbill.adapters.BillListAdapter
import com.example.automatedbill.databinding.FragmentBillCheckoutBinding
import javax.security.auth.login.LoginException

class BillCheckout : Fragment() {

    val navigationArgs: BillCheckoutArgs by navArgs()
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding:FragmentBillCheckoutBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentBillCheckoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bno = navigationArgs.billno.toString().toInt()
        binding.billno.text=bno.toString()
        Log.i("cbill", "onViewCreated: $bno")
        val adapter = BillListAdapter{
                val actionx = BillCheckoutDirections.actionBillCheckoutToEditBillItem(it.id.toString(),it.billNo.toString())
                this.findNavController().navigate(actionx)
        }
        binding.billRecycler.adapter=adapter
      //  Log.i("cbill", "onViewCreated: ")

       // Log.i("cbill","nahi chala yaha se")
            viewModel.currentBill(bno).observe(this.viewLifecycleOwner) { items ->
                    items.let {
                        adapter.submitList(it)
                    }
                }
        binding.billRecycler.layoutManager=LinearLayoutManager(this.context)
        binding.addbillnewitembtn.setOnClickListener {
                val action = BillCheckoutDirections.actionBillCheckoutToEnterManually(itemid = binding.billno.text.toString())
                findNavController().navigate(action)
            }

    }

    /*private fun getcbill(billno:Int){
        viewModel.cbill(billno)
    }*/

}