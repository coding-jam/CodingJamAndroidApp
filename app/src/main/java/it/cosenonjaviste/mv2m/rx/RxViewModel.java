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

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.cosenonjaviste.mv2m.ViewModel;

public abstract class RxViewModel<A, M extends Parcelable> extends ViewModel<A, M> {

    protected final CompositeDisposable subscription = new CompositeDisposable();

    @Override public void onDestroy(Fragment view, boolean changingConfigurations) {
        if (!changingConfigurations) {
            subscription.clear();
        }
    }

    public <T> void subscribe(Consumer<Boolean> loadingAction, Observable<T> observable, Consumer<? super T> onNext, Consumer<Throwable> onError) {
        try {
            loadingAction.accept(true);
        } catch (Exception ignored) {
        }
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.accept(false))
                .subscribe(onNext, onError)
        );
    }

    public <T> void subscribe(Consumer<Boolean> loadingAction, Single<T> observable, Consumer<? super T> onNext, Consumer<Throwable> onError) {
        try {
            loadingAction.accept(true);
        } catch (Exception ignored) {
        }
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.accept(false))
                .subscribe(onNext, onError)
        );
    }

    public <T> void subscribe(Consumer<Boolean> loadingAction, Completable observable, Action onNext, Consumer<Throwable> onError) {
        try {
            loadingAction.accept(true);
        } catch (Exception ignored) {
        }
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> loadingAction.accept(false))
                .subscribe(onNext, onError)
        );
    }
}