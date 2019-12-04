/*
package com.example.dynamiclinktest

import android.app.Application
import com.example.dynamiclinktest.di.modules.AppModule
import com.example.dynamiclinktest.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var dispatchingAndroidInjectorAny: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjectorAny


    private val appComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build()
    }
}*/
