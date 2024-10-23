package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.forms.MemberForm

data class DataFieldsReworkState(
    var isAddMembersFormShowing: Boolean = false,
    var isDropdownExpanded: Boolean = false,
    var memberData: MemberForm? = null
)
