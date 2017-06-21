package com.debla.minicomp.Activity.StandardMode;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debla.minicomp.Activity.Public.ScreenUtils;
import com.debla.minicomp.Structs.ItemView;
import com.debla.minicomp.minicomp.R;

import java.util.List;

/**
 * Created by Dave-PC on 2017/2/12.
 */

public class GridViewAdapter extends BaseAdapter{
    private Context context;
    private List<ItemView> list;
    //public static int ROW_NUMBER = 3;
    //private int[] picIds = null;
    //private DragGridView mGv;
    private int hidePosition = AdapterView.INVALID_POSITION;

    public GridViewAdapter(Context context, List<ItemView> list) {
        //this.mGv=mGv;
        this.context = context;
        this.list = list;
    }

    public List<ItemView> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            //view = new ImageView(context);
            view = LayoutInflater.from(context).inflate(R.layout.sta_grid_layout,null);
        }
        else {
            view = (View)convertView;
        }
        int screenWidth = ScreenUtils.getScreenWidth(context);
        int screenHeight = ScreenUtils.getScreenHeight(context);
        int viewWidth = screenWidth/2;
        int viewHeight = (screenHeight/4)-3;

        //hide时隐藏Text
        if(position != hidePosition) {
            //view.setText(strList.get(position));
            LinearLayout layout = (LinearLayout)view.findViewById(R.id.item_view);
            GradientDrawable myGrad = (GradientDrawable)layout.getBackground();           //从布局中取出
            myGrad.setColor(list.get(position).getColor());
            ImageView img = (ImageView)view.findViewById(R.id.item_img);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setImageResource(list.get(position).getItem_img());
            TextView tv = (TextView) view.findViewById(R.id.item_title);
            tv.setText(list.get(position).getItem_title());
            view.setVisibility(View.VISIBLE);
        }
        else {
            //view.setText("");
            view.setVisibility(View.INVISIBLE);
        }

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(viewWidth,viewHeight-20);
        view.setLayoutParams(params);
        //view.setScaleType(ImageView.ScaleType.CENTER);
        view.setId(position);
        //Log.e("debug",String.valueOf(params));

        return view;
    }

    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }


    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }


    public void removeView(int pos) {
        //strList.remove(pos);
        list.remove(pos);
        notifyDataSetChanged();
    }

    //更新拖动时的gridView
    public void swapView(int draggedPos, int destPos) {
        //从前向后拖动，其他item依次前移
        if(draggedPos < destPos) {
            list.add(destPos+1, (ItemView)getItem(draggedPos));
            list.remove(draggedPos);
        }
        //从后向前拖动，其他item依次后移
        else if(draggedPos > destPos) {
            list.add(destPos, (ItemView)getItem(draggedPos));
            list.remove(draggedPos+1);
        }
        hidePosition = destPos;
        notifyDataSetChanged();
    }
}
