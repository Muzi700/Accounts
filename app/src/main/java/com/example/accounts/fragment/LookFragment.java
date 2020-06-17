package com.example.accounts.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.accounts.R;
import com.example.accounts.activity.LookAdapter;
import com.example.accounts.activity.TotalActivity;
import com.example.accounts.base.DatabaseUtil;
import com.example.accounts.bean.Account;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LookFragment extends Fragment {

    private String mName;

    public LookFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString("name");
        }
    }
    TextView tvname;
    TextView tvTotal;
    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_look, container, false);
        tvname = view.findViewById(R.id.tvname);
        tvTotal = view.findViewById(R.id.tvTotal);
        listview = view.findViewById(R.id.listview);
         tvname.setText("用户: "+mName);
        tvTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TotalActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Account> list = DatabaseUtil.getInstance().getAllData();
        setData(list);
    }



    private void setData(List<Account> list) {
        LookAdapter adapter = new LookAdapter(getActivity(),list);
        listview.setAdapter(adapter);
    }
}