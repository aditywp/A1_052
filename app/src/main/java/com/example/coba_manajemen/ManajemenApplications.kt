package com.example.coba_manajemen

import android.app.Application
import com.example.coba_manajemen.di.AppContainer
import com.example.coba_manajemen.di.ManajemenContainer

class ManajemenApplications: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ManajemenContainer()
    }
}