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

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

class ViewModelManager<VM extends ViewModel<?, ?>> {

    private VM viewModel;

    public <F extends Fragment & ViewModelContainer<VM>> VM getOrCreate(F fragment, Bundle state) {
        VM viewModel = getOrCreate(fragment, fragment.getActivity().getSupportFragmentManager(), state, fragment.getArguments());
        return viewModel;
    }

    private VM getOrCreate(final ViewModelContainer<VM> container, FragmentManager fragmentManager, Bundle state, Bundle arguments) {
        Object args = null;
        if (arguments != null) {
            args = ArgumentManager.readArgument(arguments);
        }
        ViewModelRetainedFragment<VM> retainedFragment = ViewModelRetainedFragment.getOrCreateFragment(fragmentManager, ViewModelRetainedFragment.TAG + container.getFragmentTag(args));
        viewModel = retainedFragment.viewModel;
        if (viewModel == null) {
            viewModel = container.createViewModel();
            retainedFragment.viewModel = viewModel;
            Parcelable model = null;
            if (state != null) {
                model = state.getParcelable(ViewModel.MODEL);
            }
            ((ViewModel) viewModel).initArgumentAndModel(args, model);
        }

        viewModel.attachActivity(container);

        return viewModel;
    }

    public void resume() {
        viewModel.resume();
    }

    public void pause() {
        viewModel.pause();
    }

    public void destroy() {
        viewModel.detachView();
    }

    public void saveState(final Bundle outState) {
        outState.putParcelable(ViewModel.MODEL, viewModel.getModel());
    }
}
