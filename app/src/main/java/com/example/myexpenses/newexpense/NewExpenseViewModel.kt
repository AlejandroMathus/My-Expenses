package com.example.myexpenses.main


import androidx.lifecycle.*
import com.example.myexpenses.database.Expense
import com.example.myexpenses.database.ExpenseDatabaseDao

class NewExpenseViewModel(dataSource: ExpenseDatabaseDao) : ViewModel() {

    private val database = dataSource

    val amount = MutableLiveData<Double?>()
    val category = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<Boolean?>()
    val errorMessage: LiveData<Boolean?>
        get() = _errorMessage

    private val _navigateToMain = MutableLiveData<Boolean?>()
    val navigateToMain: LiveData<Boolean?>
        get() = _navigateToMain

    fun saveExpense() {
        val valid = isDataValid()
        if (valid) {
            val expense = buildExpense()

            database.insert(expense)

            _navigateToMain.value = true
        } else {
            _errorMessage.value = true

        }
    }

    private fun isDataValid(): Boolean {
        return amount.value != null &&
                !category.value.isNullOrEmpty()
    }

    private fun buildExpense(): Expense {
        return Expense(
            expenseAmount = amount.value!!.toDouble(),
            expenseDescription = description.value,
            expenseCategory = category.value!!
        )
    }

    fun mainNavigated() {
        _navigateToMain.value = null
    }

    fun doneShowingToast() {
        _errorMessage.value = null
    }
}

class NewExpenseViewModelFactory(
    private val dataSource: ExpenseDatabaseDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewExpenseViewModel::class.java)) {
            return NewExpenseViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
