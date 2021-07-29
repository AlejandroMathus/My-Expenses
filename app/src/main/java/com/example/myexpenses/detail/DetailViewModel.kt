package com.example.myexpenses.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myexpenses.database.Expense
import com.example.myexpenses.database.ExpenseDatabaseDao

class DetailViewModel(
    private val expenseKey: Long = 0L,
    dataSource: ExpenseDatabaseDao
) : ViewModel() {

    val database = dataSource

    private val expense: Expense

    fun getExpense() = expense


    init {
        expense = database.getExpenseWithId(expenseKey)
    }

    private val _navigateToMain = MutableLiveData<Boolean?>()
    val navigateToMain: LiveData<Boolean?>
        get() = _navigateToMain

    private var _showToastEvent = MutableLiveData<Boolean?>()
    val showToastEvent: LiveData<Boolean?>
        get() = _showToastEvent

    fun onDelete() {
        getExpense()
        //database.delete(expense)
        _navigateToMain.value = true
        _showToastEvent.value = true
    }

    fun onClose() {
        _navigateToMain.value = true
    }

    fun doneNavigating() {
        _navigateToMain.value = null
    }

    fun doneShowingToast() {
        _showToastEvent.value = null
    }

}

class DetailViewModelFactory(
    private val expenseKey: Long,
    private val dataSource: ExpenseDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(expenseKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}