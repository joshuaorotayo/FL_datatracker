package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

interface DataFieldsReworkInterface {
    fun initView()
    fun onAddMembersClicked()
}

val dataFieldsReworkPreview = object : DataFieldsReworkInterface {
    override fun initView() = Unit

    override fun onAddMembersClicked() = Unit
}