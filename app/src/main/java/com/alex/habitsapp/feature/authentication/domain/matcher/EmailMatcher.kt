package com.alex.habitsapp.feature.authentication.domain.matcher

interface EmailMatcher {
    fun isValid(email: String): Boolean
}