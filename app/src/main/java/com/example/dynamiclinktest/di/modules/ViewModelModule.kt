/*
package com.example.dynamiclinktest.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dynamiclinktest.di.ViewModelKey
import com.example.dynamiclinktest.presentation.FirebaseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kz.mobile.mgov.core.di.factories.DaggerViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FirebaseViewModel::class)
    internal abstract fun provideFirebaseViewModel(viewModel: FirebaseViewModel): ViewModel

}*/
