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
package it.cosenonjaviste.mv2m.rx;


import android.os.Parcelable;
import android.support.v4.app.Fragment;

import it.cosenonjaviste.mv2m.ViewModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class RxViewModel<A, M extends Parcelable> extends ViewModel<A, M> {

    protected final CompositeSubscription subscription = new CompositeSubscription();

    @Override public void onDestroy(Fragment view, boolean changingConfigurations) {
        if (!changingConfigurations) {
            subscription.clear();
        }
    }

    public <T> void subscribe(Action1<Boolean> loadingAction, Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        loadingAction.call(true);
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.call(false))
                .subscribe(onNext, onError)
        );
    }
}