package com.jorotayo.fl_datatracker.ui.util.components.toasts


// =============================================================================
// TOAST DATA
// =============================================================================

/**
 * Holds everything needed to show a toast. Place this in your screen state
 * and set it to null to hide the toast.
 *
 * @param message       Main message text.
 * @param mode          Visual mode — INFO, WARNING, or ERROR.
 * @param actionLabel   Optional tappable label shown below the message.
 * @param onAction      Called when the card or action label is tapped.
 * @param leadingIcon   Replaces the default mode icon. Null keeps the default.
 * @param trailingIcon  Extra icon on the trailing end, before the dismiss X.
 * @param durationMs    Auto-dismiss delay in ms. Null = manual dismiss only.
 */
data class AppToastData(
    val message: String,
    val mode: ToastMode = ToastMode.INFO,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val leadingIcon: ToastIcon? = null,
    val trailingIcon: ToastIcon? = null,
    val durationMs: Long? = 4000L
)