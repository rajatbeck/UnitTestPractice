package com.learn.unittestdemo.di;


import androidx.lifecycle.ViewModelProvider;

import com.learn.unittestdemo.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {


    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);



}
