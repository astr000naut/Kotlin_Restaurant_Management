package com.example.sample.ui.bep.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.model.database.BpDishDao

class DishListViewModelFactory(private val dao: BpDishDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishListViewModel::class.java)) {
            return DishListViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}