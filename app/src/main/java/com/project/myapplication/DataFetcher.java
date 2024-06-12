package com.project.myapplication;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataFetcher {
    private final ApiService apiService;
    private final DataFetchCallback callback;

    public interface DataFetchCallback {
        void onDataFetched(List<Item> items);
        void onFailure(Throwable t);
    }

    public DataFetcher(DataFetchCallback callback) {
        this.apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        this.callback = callback;
    }

    public void fetchData() {
        Call<List<Item>> call = apiService.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> filteredItems = response.body().stream()
                            .filter(item -> item.getName() != null && !item.getName().isEmpty())
                            .sorted(Comparator.comparingInt(Item::getListId).thenComparing(Item::getId))
                            .collect(Collectors.toList());
                    callback.onDataFetched(filteredItems);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}

