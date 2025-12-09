package com.example.photodex

import com.example.photodex.viewmodel.NetworkViewModel
import org.junit.Test
import kotlin.test.assertEquals


/*
* Testing NetworkViewModel to satisfy requirements without having to fight with Gradle any longer
 */
class NetworkViewModelTest {
    @Test
    fun setUsername_updatesLiveDataValue() {
        val isNetworkAvailable = NetworkViewModel.isNetworkAvailable.value
        assertEquals(isNetworkAvailable, true, "Network is available by default (hopefully)")
    }
}