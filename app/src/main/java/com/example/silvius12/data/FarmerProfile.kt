package com.example.silvius12.data

/** One farmer, as onboarding builds them up across the two form screens. */
data class FarmerProfile(
    val id: Long = 0L,
    val fullName: String = "",
    val nationalId: String = "",
    val country: String = "",
    val mainCrop: String = "",
    val countryCode: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
) {
    /** E.164-ish display form, e.g. "+251 912345678". */
    val fullPhone: String
        get() = listOf(countryCode, phoneNumber).filter { it.isNotBlank() }.joinToString(" ")
}
