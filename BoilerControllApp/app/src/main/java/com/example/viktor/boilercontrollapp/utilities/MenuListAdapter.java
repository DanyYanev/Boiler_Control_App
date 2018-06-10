package com.example.viktor.boilercontrollapp.utilities;

/**
 * Created by viktor on 4/25/18.
 */
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktor.boilercontrollapp.R;

import java.util.ArrayList;

/**
 * Created by Anthony on 16-01-25.
 */
public class MenuListAdapter extends ArrayAdapter<MenuActionItem> {

    int resource;
    Activity activity;

    public MenuListAdapter(int resource, Activity activity, MenuActionItem[] items) {
        super(activity, resource, items);

        this.resource = resource;
        this.activity = activity;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            rowView = activity.getLayoutInflater().inflate(resource, null);

            MenuItemViewHolder viewHolder = new MenuItemViewHolder();

            viewHolder.menuItemImageView = (ImageView)rowView.findViewById(R.id.menu_item_image_view);
            viewHolder.menuItemTextView = (TextView)rowView.findViewById(R.id.menu_item_text_view);

            rowView.setTag(viewHolder);
        }

        MenuItemViewHolder holder = (MenuItemViewHolder)rowView.getTag();

        if(position == MenuActionItem.ITEM1.ordinal()) {
            holder.menuItemImageView.setImageDrawable(activity.getDrawable(R.drawable.ic_home));
            holder.menuItemTextView.setText(activity.getResources().getString(R.string.item1));
        }else if(position == MenuActionItem.ITEM2.ordinal()){
            holder.menuItemImageView.setImageDrawable(activity.getDrawable(R.drawable.ic_heater));
            holder.menuItemTextView.setText(activity.getResources().getString(R.string.item3));
        }else if(position == MenuActionItem.ITEM3.ordinal()) {
            holder.menuItemImageView.setImageDrawable(activity.getDrawable(R.drawable.ic_thermometer));
            holder.menuItemTextView.setText(activity.getResources().getString(R.string.item2));
        }else if(position == MenuActionItem.ITEM4.ordinal()) {
            holder.menuItemImageView.setImageDrawable(activity.getDrawable(R.drawable.ic_gears));
            holder.menuItemTextView.setText(activity.getResources().getString(R.string.item4));
        }
        return rowView;
    }

    private static class MenuItemViewHolder {
        public ImageView menuItemImageView;
        public TextView menuItemTextView;
    }
}