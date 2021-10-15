package com.example.automatedbill

import androidx.lifecycle.*
import com.example.automatedbill.room.Item
import com.example.automatedbill.room.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao):ViewModel() {

    val allItems:LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun retrieveItem(id:Int):LiveData<Item>{
        return itemDao.getItem(id).asLiveData()
    }

    fun deleteItem(item: Item){
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
    private fun getUpdatedItemEntry(id:Int,name:String,price: String,quantity: String):Item{
        return Item(
            id=id,
            itemName = name,
            itemPrice = price.toDouble(),
            itemQuantity = quantity.toInt()
        )
    }
    fun updateItem(id:Int,name: String,price: String,quantity: String){
        val updatedItem=getUpdatedItemEntry(id,name,price,quantity)
        updateItem(updatedItem)
    }
    private fun updateItem(item: Item){
        viewModelScope.launch {
            itemDao.update(item)
        }
    }
    private fun insertItem(item:Item){
        viewModelScope.launch{
            itemDao.insert(item)
        }
    }
    private fun getNewItemEntry(item:String, price:String,quantity:String):Item{
        return Item(
            itemName = item,
            itemPrice = price.toDouble(),
            itemQuantity = quantity.toInt()
        )
    }
    fun addNewItem(item:String,price:String,quantity: String){
        val newItem=getNewItemEntry(item,price,quantity)
        insertItem(newItem)
    }
    fun isEntryValid(item:String,price:String,quantity:String):Boolean{
        if(item.isBlank()||price.isBlank()||quantity.isBlank()){
            return false
        }
        return true
    }

}
class InventoryViewModelFactory(private val itemDao: ItemDao):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("Unchecked_Cast")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}