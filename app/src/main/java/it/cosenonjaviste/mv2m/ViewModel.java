/*
 *  Copyright 2015 Fabio Collini.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.mv2m;

import android.app.Activity;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import it.codingjam.lifecyclebinder.DefaultLifeCycleAware;

public abstract class ViewModel<A, M extends Parcelable> extends DefaultLifeCycleAware<Fragment> {

    public static final String MODEL = "model";

    protected M model;

    protected A argument;

    protected final ActivityHolder activityHolder = new ActivityHolder();

    /**
     * @deprecated Use activityHolder.getActivity() instead.
     */
    @Deprecated
    protected Activity activity;

    @Override public void onPause(Fragment view) {
        pause();
    }

    public void pause() {
    }

    @Override public void onResume(Fragment view) {
        resume();
    }

    public void resume() {
    }

    @Override public void onDestroy(Fragment view, boolean changingConfigurations) {
        detachView();
        if (!changingConfigurations) {
            destroy();
        }
    }

    public void destroy() {
    }

    public void detachView() {
        activityHolder.setViewModelContainer(null);
        activity = null;
    }

    @NonNull protected abstract M createModel();

    public void initArgumentAndModel(A arguments, M model) {
        this.argument = arguments;
        this.model = model != null ? model : createModel();
    }

    public M initAndResume() {
        return initAndResume(null);
    }

    public M initAndResume(A arguments) {
        initArgumentAndModel(arguments, null);
        resume();
        return model;
    }

    public M getModel() {
        return model;
    }

    public A getArgument() {
        return argument;
    }

    public final void attachActivity(ViewModelContainer<?> view) {
        this.activityHolder.setViewModelContainer(view);
        activity = view.getActivity();
    }
}
