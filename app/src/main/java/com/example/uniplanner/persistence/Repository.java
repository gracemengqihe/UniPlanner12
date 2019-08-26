package com.example.uniplanner.persistence;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class Repository {

    private final Access access;

    public Repository(Access access) {
        this.access = access;
    }

    public int count(long id) {
        return AsyncAccess.run(() -> access.count(id));
    }

    public int countByType(long id, String type) {
        return AsyncAccess.run(() -> access.countByType(id, type));
    }

    public void delete(CourseSelection... selections) {
        AsyncAccess.run(() -> access.delete(selections));
    }

    public CourseSelection getSelection(long id, int year, int term, int choice) {
        return AsyncAccess.run(() -> access.getSelection(id, year, term, choice));
    }

    public List<CourseSelection> getSelections(long id) {
        return AsyncAccess.run(() -> access.getSelections(id));
    }

    public boolean hasSelected(long id, int year, int term, int choice) {
        return AsyncAccess.run(() -> access.hasSelected(id, year, term, choice));
    }

    public void insert(CourseSelection... selections) {
        AsyncAccess.run(() -> access.insert(selections));
    }

    public boolean isTaking(long id, String course) {
        return AsyncAccess.run(() -> access.isTaking(id, course));
    }

    public static class AsyncAccess<Result> extends AsyncTask<Void, Void, Result> {

        private final Supplier<Result> supplier;

        public AsyncAccess(Supplier<Result> supplier) {
            this.supplier = supplier;
        }

        @Override
        public Result doInBackground(Void... none) {
            return supplier.get();
        }

        public static void run(Runnable runnable) {
            new AsyncAccess<>(() -> new VoidSupplier(runnable).get()).execute();
        }

        public static <Result> Result run(Supplier<Result> supplier) {
            try {
                return new AsyncAccess<>(() -> supplier.get()).execute().get();

            } catch (ExecutionException | InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class VoidSupplier implements Supplier<Void> {

        private final Runnable runnable;

        public VoidSupplier(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public Void get() {
            runnable.run();
            return null;
        }
    }
}