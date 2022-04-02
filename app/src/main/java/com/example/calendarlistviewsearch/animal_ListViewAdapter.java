package com.example.calendarlistviewsearch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FilterReader;
import java.util.ArrayList;

public class animal_ListViewAdapter extends BaseAdapter implements Filterable {
    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList.(원본 데이터 리스트)
    private ArrayList<animal_ListViewItem> listViewItemList = new ArrayList<animal_ListViewItem>();
    //필터링된 결과 데이터를 저장하기 위한 ArrayList.최초에는 전체 리스트 보유.
    private ArrayList<animal_ListViewItem> filteredItemList = listViewItemList ;

    Filter listFilter ;

    //ListViewAdapter 의 생성자
    public animal_ListViewAdapter() {

    }

    //Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    //position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //"Listview_item" Layout을 inflate하여 convertView 참조 획득.
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.animal_listview_item,parent,false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2);

        //Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        animal_ListViewItem listViewItem = filteredItemList.get(position);

        //아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());



        return convertView;
    }

    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴.: 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    //아이템 데이터 추가를 위한 함수. 개발자가 우너하는대로 작성 가능.
    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    //아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon,String title, String desc) {
        animal_ListViewItem item = new animal_ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    //TODO : filtering item.



    @Override
    public Filter getFilter() {
        if(listFilter == null) {
            listFilter = new ListFilter();
        }
        return listFilter ;
    }

    private class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0) {
                results.values = listViewItemList;
                results.count = listViewItemList.size();
            }else{
                ArrayList<animal_ListViewItem> itemList = new ArrayList<animal_ListViewItem>();

                for (animal_ListViewItem item : listViewItemList) {
                    if(item.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getDesc().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ;
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return  results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            //update listview by filtered data list.
            filteredItemList = (ArrayList<animal_ListViewItem>)results.values;

            //notify
            if(results.count > 0) {
                notifyDataSetChanged() ;
            }else {
                notifyDataSetInvalidated();
            }
        }
    }
}