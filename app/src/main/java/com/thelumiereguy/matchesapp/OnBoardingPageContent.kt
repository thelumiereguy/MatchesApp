package com.thelumiereguy.matchesapp

data class OnBoardingPageContent(
    val description: String,
    val imageId: Int
) {

    companion object{
        fun getOnBoardingContent():List<OnBoardingPageContent>{
            return listOf(
                OnBoardingPageContent("Hello",R.drawable.ic_heart),
                OnBoardingPageContent("Not Hello",R.drawable.ic_heart)
            )
        }
    }

}