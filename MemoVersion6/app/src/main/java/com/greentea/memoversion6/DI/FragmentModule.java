package com.greentea.memoversion6.DI;

import com.greentea.memoversion6.component.activity.fragment.memoAdd.MemoAddFragment;
import com.greentea.memoversion6.component.activity.fragment.memoBody.MemoBodyFragment;
import com.greentea.memoversion6.component.activity.fragment.memoOverallSetting.MemoOverallSettingFragment;
import com.greentea.memoversion6.component.activity.fragment.memoTimeSetting.MemoTimeSettingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Philippe on 02/03/2018.
 */

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MemoAddFragment contributeMemoAddFragment();

    @ContributesAndroidInjector
    abstract MemoBodyFragment contributeMemoBodyFragment();

    @ContributesAndroidInjector
    abstract MemoTimeSettingFragment contributeMemoTimeSettingFragment();

    @ContributesAndroidInjector
    abstract MemoOverallSettingFragment contributeMemoOverallTimeSettingFragment();
}
