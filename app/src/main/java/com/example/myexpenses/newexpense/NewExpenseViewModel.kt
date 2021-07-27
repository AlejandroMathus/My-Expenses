package com.example.myexpenses.main


import androidx.lifecycle.*
import com.example.myexpenses.database.Expense
import com.example.myexpenses.database.ExpenseDatabaseDao

class NewExpenseViewModel(dataSource: ExpenseDatabaseDao) : ViewModel() {

    val database = dataSource
    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String>
        get() = _amount

    private val _category = MutableLiveData<String>()
    val category: LiveData<String>
        get() = _category

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

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
        return _amount.value != null &&
                !_category.value.isNullOrEmpty()
    }

    private fun buildExpense(): Expense {
        return Expense(
            expenseAmount = _amount.value!!.toDouble(),
            expenseDescription = _description.value,
            expenseCategory = _category.value!!
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
