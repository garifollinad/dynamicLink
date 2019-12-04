/*
package com.example.dynamiclinktest.di.component

import android.app.Application
import com.example.dynamiclinktest.App
import com.example.dynamiclinktest.di.modules.ActivityModule
import com.example.dynamiclinktest.di.modules.AppModule
import com.example.dynamiclinktest.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class
        //ActivityModule::class,
       // ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}*/
