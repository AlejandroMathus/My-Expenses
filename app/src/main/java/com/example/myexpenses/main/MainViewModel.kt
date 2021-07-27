package com.example.myexpenses.main

import android.app.Application
import androidx.lifecycle.*
import com.example.myexpenses.database.ExpenseDatabaseDao

class MainViewModel(
    dataSource: ExpenseDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val database = dataSource

    val expenses = database.getAllExpenses()

    // Navigation to the New Expense Fragment.
    private val _navigateToNewExpense = MutableLiveData<Boolean>()
    val navigateToNewExpense: LiveData<Boolean>
        get() = _navigateToNewExpense

    fun navigateToNewExpense() {
        _navigateToNewExpense.value = true
    }

    fun newExpenseFinishedNavigating() {
        _navigateToNewExpense.value = false
    }

    // Navigation to the Expense Detail Fragment.
    private val _navigateToExpenseDetail = MutableLiveData<Long>()
    val navigateToExpenseDetail
        get() = _navigateToExpenseDetail

    fun onExpenseClicked(expenseId: Long) {
        _navigateToExpenseDetail.value = expenseId
    }

    fun onExpenseDetailNavigated() {
        _navigateToExpenseDetail.value = null
    }
}

class MainViewModelFactory(
    private val dataSource: ExpenseDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}