package com.example.accounts.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accounts.R;
import com.example.accounts.activity.MainActivity;
import com.example.accounts.base.DatabaseUtil;
import com.example.accounts.bean.Account;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private String type;//方式
    private EditText etPrice;
    private TextView tvData;
    private EditText etRemark;
    private String date="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Spinner spinner = view.findViewById(R.id.spinner);
        etPrice =view.findViewById(R.id.etPrice);
        tvData =view.findViewById(R.id.tvData);
        etRemark =view.findViewById(R.id.etRemark);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                type = getResources().getStringArray(R.array.account)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
        return view;
    }

    private void showDate() {
        Calendar calender = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int
                    dayOfMonth) {
                String mouth1 = "";
                String day1 = "";
                if (monthOfYear < 9) {
                    mouth1 = "0" + (monthOfYear + 1);
                } else {
                    mouth1 = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth <= 9) {
                    day1 = "0" + dayOfMonth;
                } else {
                    day1 = String.valueOf(dayOfMonth);
                }
                date = String.valueOf(year) + "-" + mouth1 + "-" + day1;
                tvData.setText(date);
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void add(){
        String price = etPrice.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();
        if (price.isEmpty()){
            showMsg("金额不能为空");
        }else if (date.isEmpty()){
            showMsg("请选择日期");
        }else if (remark.isEmpty()){
            showMsg("备注不能为空");
        }else {
            Account account = new Account();
            account.setDate(date);
            account.setPrice(price);
            account.setRemark(remark);
            account.setType(type);
            long flag = DatabaseUtil.getInstance().inisertAccount(account);
            if (flag>0 || flag==-1){
                showMsg("添加成功");
            }
            Log.e("flag","++"+flag);
        }
    }


    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

}