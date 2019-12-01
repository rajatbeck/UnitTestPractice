package com.learn.unittestdemo.di;

import android.app.Application;

import com.learn.unittestdemo.BaseApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                ActivityBuilderModule.class,
                ViewModelFactoryModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
     interface  Builder{

        @BindsInstance
         Builder application(Application application);

         AppComponent build();
    }

}
