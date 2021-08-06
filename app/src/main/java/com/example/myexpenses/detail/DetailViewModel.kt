package com.example.myexpenses.detail

import androidx.lifecycle.*
import com.example.myexpenses.database.Expense
import com.example.myexpenses.database.ExpenseDatabaseDao
import kotlinx.coroutines.launch

class DetailViewModel(
    private val expenseKey: Long = 0L,
    dataSource: ExpenseDatabaseDao
) : ViewModel() {

    val database = dataSource

    private val expense: LiveData<Expense> = database.getExpenseWithId(expenseKey)

    fun getExpense() = expense

    private val _navigateToMain = MutableLiveData<Boolean?>()
    val navigateToMain: LiveData<Boolean?>
        get() = _navigateToMain

    private var _showToastEvent = MutableLiveData<Boolean?>()
    val showToastEvent: LiveData<Boolean?>
        get() = _showToastEvent

    fun onDelete() {
        val thisExpense: Expense = expense.value!!
        delete(thisExpense)
        _navigateToMain.value = true
        _showToastEvent.value = true
    }

    private fun delete(expense: Expense) = viewModelScope.launch {
        database.delete(expense)
    }

    fun doneShowingToast() {
        _showToastEvent.value = null
    }

    fun onClose() {
        _navigateToMain.value = true
    }

    fun doneNavigating() {
        _navigateToMain.value = null
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