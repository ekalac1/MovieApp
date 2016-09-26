package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.SearchResultsAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public List<Movie> searchResultMovies;
    public String query;
    public List<Movie> searchResult;
    public int pageNum;
    public String lastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        pageNum=1;
        handleIntent(getIntent());
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String queryOnCreate = sharedPref.getString("idSet", "");
        if (queryOnCreate.length()>=3)
        {
            final GridView grid = (GridView)findViewById(R.id.gridView);
            if (ApiClient.API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.api_key_missing, Toast.LENGTH_LONG).show();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


            Call<MoviesListResponse> call = apiService.getSearchedMovies(ApiClient.API_KEY, queryOnCreate, pageNum);
            call.enqueue(new Callback<MoviesListResponse>() {
                @Override
                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                    searchResultMovies = response.body().getResults();
                    pageNum++;
                    final SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.search_view_element, searchResultMovies);
                    grid.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                }
            });
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
                    intent.putExtra("id", searchResultMovies.get(position).getId());
                    startActivity(intent);

                }
            });
            grid.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<MoviesListResponse> call = apiService.getSearchedMovies(ApiClient.API_KEY, query, pageNum);
                    call.enqueue(new Callback<MoviesListResponse>() {
                        @Override
                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {

                            List<Movie> temp = response.body().getResults();
                            searchResultMovies.addAll(temp);
                            pageNum++;
                            ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, t.toString());
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                        }
                    });


                    return true;
                }
            });
        }




    }
    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        if (!intent.getStringExtra("query").equals("")) {
            query = intent.getStringExtra("query");

            final GridView grid = (GridView)findViewById(R.id.gridView);
            if (ApiClient.API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.api_key_missing, Toast.LENGTH_LONG).show();
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


            Call<MoviesListResponse> call = apiService.getSearchedMovies(ApiClient.API_KEY, query, pageNum);
            call.enqueue(new Callback<MoviesListResponse>() {
                @Override
                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                    searchResultMovies = response.body().getResults();
                    pageNum++;
                    final SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.search_view_element, searchResultMovies);
                    grid.setAdapter(adapter);



                }

                @Override
                public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                }
            });

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
                    intent.putExtra("id", searchResultMovies.get(position).getId());
                    startActivity(intent);

                }
            });


                    grid.setOnScrollListener(new EndlessScrollListener() {
                        @Override
                        public boolean onLoadMore(int page, int totalItemsCount) {
                            ApiInterface apiService =
                                    ApiClient.getClient().create(ApiInterface.class);
                            Call<MoviesListResponse> call = apiService.getSearchedMovies(ApiClient.API_KEY, query, pageNum);
                            call.enqueue(new Callback<MoviesListResponse>() {
                                @Override
                                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {

                                    List<Movie> temp = response.body().getResults();
                                    searchResultMovies.addAll(temp);
                                    pageNum++;
                                    ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                                    // Log error here since request failed
                                    Log.e(TAG, t.toString());
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                }
                            });


                            return true;
                        }
                    });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchMenuItem.expandActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() >= 3) {
                    final GridView grid = (GridView) findViewById(R.id.gridView);
                    if (ApiClient.API_KEY.isEmpty()) {
                        Toast.makeText(getApplicationContext(), R.string.api_key_missing, Toast.LENGTH_LONG).show();
                    }

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    pageNum = 1;

                    lastQuery=newText;


                    Call<MoviesListResponse> call = apiService.getSearchedMovies(ApiClient.API_KEY, newText, pageNum);
                    call.enqueue(new Callback<MoviesListResponse>() {
                        @Override
                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                            searchResultMovies = response.body().getResults();
                            pageNum++;
                            final SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.search_view_element, searchResultMovies);
                            grid.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, t.toString());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {


        super.onDestroy();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("idSet", lastQuery);
        editor.commit();
       /* Intent proba = new Intent(this, MainActivity.class);
        startActivity(proba); */
    }


}

