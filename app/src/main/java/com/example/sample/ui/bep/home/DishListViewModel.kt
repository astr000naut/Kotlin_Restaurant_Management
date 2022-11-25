package com.example.sample.ui.bep.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.model.BP_Dish
import com.example.sample.model.database.BpDishDao
import io.socket.client.Socket
import kotlinx.coroutines.launch

class DishListViewModel(val dao: BpDishDao) : ViewModel() {
    val bpDishList = dao.getAll()

    fun addBpDish(bpDish: BP_Dish) {
        viewModelScope.launch {
            dao.insert(bpDish)
        }
    }
    fun removeBpDish(bpDish: BP_Dish) {
        viewModelScope.launch {
            dao.delete(bpDish)
        }
    }
    fun changeStateBpDish(bpDish: BP_Dish) {
        viewModelScope.launch {
            dao.changestate(bpDish.id)
        }
    }
}