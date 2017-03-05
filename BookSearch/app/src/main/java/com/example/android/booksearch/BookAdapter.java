package com.example.android.booksearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ftf-icn on 2/19/2017.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_books, parent, false);
        }

        Book currentBook = getItem(position);
        TextView title_holder = (TextView) listItemView.findViewById(R.id.title);
        title_holder.setText(currentBook.getTitle());

        TextView author_holder = (TextView) listItemView.findViewById(R.id.author);
        author_holder.setText(currentBook.getAuthor());

        TextView date_holder = (TextView) listItemView.findViewById(R.id.date);
        date_holder.setText(currentBook.getDate());

        View parentView = (View) listItemView.findViewById(R.id.parent);
        if (position % 2 != 0) {
            parentView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return listItemView;
    }
}
