package com.example.sample.ui.bep.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.model.database.BpDishDao
import io.socket.client.Socket

class DishListViewModelFactory(private val dao: BpDishDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishListViewModel::class.java)) {
            return DishListViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}