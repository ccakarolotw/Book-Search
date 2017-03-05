package com.example.android.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static final int LOADER_ID = 1;
    private BookAdapter mAdapter;
    private String REQUEST_URL =
            " https://www.googleapis.com/books/v1/volumes?q=intitle:";
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        String searchInput;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            searchInput = null;
        } else {
            REQUEST_URL = REQUEST_URL + extras.getString("searchText").replaceAll(" ", "+") + "&orderBy=newest&maxResults=40&langRestrict=en";
        }

        ListView bookListView = (ListView) findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);
        bookListView.setEmptyView(mEmptyStateTextView);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BookListActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_books);
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
