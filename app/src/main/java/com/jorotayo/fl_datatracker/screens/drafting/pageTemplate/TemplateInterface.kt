package com.jorotayo.fl_datatracker.screens.drafting.pageTemplate

interface TemplateInterface {
    fun initView()
    fun onAddMembersClicked()
}

val templatePreview = object : TemplateInterface {
    override fun initView() = Unit

    override fun onAddMembersClicked() = Unit
}