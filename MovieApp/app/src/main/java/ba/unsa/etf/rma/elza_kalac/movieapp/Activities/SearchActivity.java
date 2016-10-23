package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.SearchResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.SearchResultsAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.SearchResults;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    public List<SearchResults> searchResult = new ArrayList<>();
    public String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        final GridView grid = (GridView) findViewById(R.id.gridView);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        query = sharedPref.getString("idSet", "");
        if (query != "") {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<SearchResponse> call = apiService.getSearchedItems(ApiClient.API_KEY, query, 1);
            call.enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    searchResult = response.body().getResults();
                    for (int i = 0; i < searchResult.size(); i++) {

                        if (searchResult.get(i).getMediaType().equals("person") )
                            searchResult.remove(i);
                    }
                    final SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.search_view_element, searchResult);
                    grid.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                }
            });
        }


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (searchResult.get(position).getMediaType().equals("movie")) {
                    Intent movieIntent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
                    movieIntent.putExtra("id", searchResult.get(position).getId());
                    startActivity(movieIntent);
                }
                else if (searchResult.get(position).getMediaType().equals("tv"))
                {
                    Intent tvIntent = new Intent(getApplicationContext(), TVShowDetails.class);
                    tvIntent.putExtra("id", searchResult.get(position).getId());
                    startActivity(tvIntent);
                }
            }
        });
        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<SearchResponse> call = apiService.getSearchedItems(ApiClient.API_KEY, query, page);
                call.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        List<SearchResults> temp = response.body().getResults();
                        for (int i = 0; i < temp.size(); i++) {
                            if (temp.get(i).getMediaType().equals("person") )
                                temp.remove(i);
                        }
                        searchResult.addAll(temp);
                        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchMenuItem.expandActionView();
        final GridView grid = (GridView) findViewById(R.id.gridView);
        searchView.setQuery(query, false);
        searchView.setBackgroundResource(R.color.trailer_bcg);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query1) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    query = newText;
                    Call<SearchResponse> call = apiService.getSearchedItems(ApiClient.API_KEY, newText, 1);
                    call.enqueue(new Callback<SearchResponse>() {
                        @Override
                        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                            searchResult = response.body().getResults();
                            for (int i = 0; i < searchResult.size(); i++) {

                                if (searchResult.get(i).getMediaType().equals("person") )
                                    searchResult.remove(i);
                            }
                            final SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.search_view_element, searchResult);
                            grid.setAdapter(adapter);
                        }
                        @Override
                        public void onFailure(Call<SearchResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("idSet", query);
        editor.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

