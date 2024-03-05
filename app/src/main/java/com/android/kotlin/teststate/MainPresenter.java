package com.android.kotlin.teststate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

public class MainPresenter implements SavedStateRegistry.SavedStateProvider {

    private final String COUNT_KEY = "key";

    private final String PROVIDER = "oo";

    private int count = 0;

    private final MainView view;

    public MainPresenter(MainView view, SavedStateRegistryOwner registryOwner) {
        this.view = view;
        registryOwner.getLifecycle().addObserver((LifecycleEventObserver) (lifecycleOwner, event) -> {
            if(event == Lifecycle.Event.ON_CREATE) {
                SavedStateRegistry registry = registryOwner.getSavedStateRegistry();
                if(registry.getSavedStateProvider(PROVIDER) == null) {
                    registry.registerSavedStateProvider(PROVIDER, this);
                }
                Bundle savedState = registry.consumeRestoredStateForKey(PROVIDER);
                if (savedState != null) setCount(savedState.getInt(COUNT_KEY));
            }
        });
    }

    public void setCount(int count) {
        this.count = count;
        view.setText(String.valueOf(count));
    }

    @NonNull
    @Override
    public Bundle saveState() {
        Bundle bundle = new Bundle();
        bundle.putInt(COUNT_KEY, count);
        return bundle;
    }
}
