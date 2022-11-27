package com.example.sample.ui.bep.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.model.BP_Dish
import com.example.sample.model.database.BpDishDao
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
    fun removeBpDishById(id: Int) {
        viewModelScope.launch {
            dao.deleteById(id)
        }
    }
    fun updateBpDishById(id: Int, soluong: Int, ghichu: String) {
        viewModelScope.launch {
            dao.updateById(id, soluong, ghichu)
        }
    }

    fun changeStateBpDish(bpDish: BP_Dish) {
        viewModelScope.launch {
            dao.changestate(bpDish.id)
        }
    }
}