package com.greentea.memoversion6.DI;

import com.greentea.memoversion6.component.service.Alarm.Service.AlarmService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract AlarmService contributeAlarmService();
}
