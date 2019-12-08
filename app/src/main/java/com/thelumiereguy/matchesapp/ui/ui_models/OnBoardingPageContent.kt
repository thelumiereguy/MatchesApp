package com.thelumiereguy.matchesapp.ui.ui_models

import com.thelumiereguy.matchesapp.R

data class OnBoardingPageContent(
    val description: String,
    val imageId: Int
) {

    companion object{
        fun getOnBoardingContent():List<OnBoardingPageContent>{
            return listOf(
                OnBoardingPageContent(
                    "Find your match",
                    R.mipmap.onboarding_1
                ),
                OnBoardingPageContent(
                    "Every love story is beautiful, but make yours unique",
                    R.mipmap.onboarding_2
                )
            )
        }
    }

}