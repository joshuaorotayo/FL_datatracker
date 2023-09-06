package com.jorotayo.fl_datatracker.screens.homeScreen.components

import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import io.objectbox.Box

data class TestState(
    val _itemsBox: Box<TestRowItem> = ObjectBox.boxStore().boxFor(TestRowItem::class.java),
    val itemsList: List<TestRowItem> = _itemsBox.all.toList(),
)
