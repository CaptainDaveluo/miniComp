package com.debla.minicomp.Activity.Contacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.debla.minicomp.Activity.Contacts.view.ContactSortModel;
import com.debla.minicomp.Activity.Contacts.view.EditTextWithDel;
import com.debla.minicomp.Activity.Contacts.view.PinyinComparator;
import com.debla.minicomp.Activity.Contacts.view.PinyinUtils;
import com.debla.minicomp.Activity.Contacts.view.SideBar;
import com.debla.minicomp.minicomp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dave-PC on 2017/2/22.
 */

public class ContactsActivity extends Activity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog, mTvTitle;
    private SortAdapter adapter;
    private EditTextWithDel mEtSearchName;
    private List<ContactSortModel> SourceDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);
        initViews();
    }

    private void initViews() {
        mEtSearchName = (EditTextWithDel) findViewById(R.id.et_search);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        sortListView = (ListView) findViewById(R.id.lv_contact);
        initDatas();
        initEvents();
        setAdapter();
    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private void setAdapter() {
        List<String> contactlist = new ArrayList<String>();
        Cursor cursor = getApplicationContext().getContentResolver().query(Phone.CONTENT_URI,
                null, null, null, null);
        int i =0;
        while(cursor.moveToNext()){
            String name=cursor.getString(cursor
                    .getColumnIndex(Phone.DISPLAY_NAME));
            if(!isNumeric(name)) {
                contactlist.add(i, name);
                i++;
            }
        }
        SourceDateList = filledData(contactlist);
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
    }

    private void initEvents() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mTvTitle.setText(((ContactSortModel) adapter.getItem(position - 1)).getName());
                Toast.makeText(getApplication(), ((ContactSortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        mEtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initDatas() {
        sideBar.setTextView(dialog);
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactSortModel> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (ContactSortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        adapter.updateListView(mSortList);
    }

    private List<ContactSortModel> filledData(List<String> date) {
        List<ContactSortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            ContactSortModel sortModel = new ContactSortModel();
            sortModel.setName(date.get(i));
            String pinyin = PinyinUtils.getPingYin(date.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();
            while(sortString.matches("[0-9]")){
                pinyin=pinyin.substring(1,pinyin.length()).toUpperCase();
                sortString=pinyin.substring(0, 1).toUpperCase();
            }
            Log.e("debug",sortString);
            indexString.add(sortString);
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sideBar.setIndexText(indexString);
        return mSortList;
    }
}
