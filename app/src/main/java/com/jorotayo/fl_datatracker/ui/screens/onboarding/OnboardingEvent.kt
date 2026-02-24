package com.jorotayo.fl_datatracker.ui.screens.onboarding

sealed class OnboardingEvent {
    object GetStarted : OnboardingEvent()
    object SaveOnBoarding : OnboardingEvent()
    object ToggleDontShowAgain : OnboardingEvent()
}
