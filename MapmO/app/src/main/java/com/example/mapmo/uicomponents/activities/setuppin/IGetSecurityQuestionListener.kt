package com.example.mapmo.uicomponents.activities.setuppin

import com.example.mapmo.models.SecurityQuestionModel
import com.example.mapmo.uicomponents.activities.settings.SettingsActivity

interface IGetSecurityQuestionListener {

    fun fetchSecurityQstnListener(securityQuestion : SecurityQuestionModel?, operation: SettingsActivity.OPERATION)
}