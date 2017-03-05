package com.example.android.booksearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView searchButton = (ImageView) findViewById(R.id.search_button);
        final EditText searchText = (EditText) findViewById(R.id.search);
        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchInput = searchText.getText().toString();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                        intent.putExtra("searchText", searchInput);
                        startActivity(intent);
                    }
                }
        );
    }
}
