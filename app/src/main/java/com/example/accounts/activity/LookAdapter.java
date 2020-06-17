package com.example.accounts.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.accounts.R;
import com.example.accounts.bean.Account;

import java.util.List;

public class LookAdapter extends BaseAdapter {
    private Context context = null;
    private List<Account> list;
    private OnMemoface onLookface;
    private OnMemoface onUpdateface;
    private OnMemoface onDeteleface;

    public void setOnDeteleface(OnMemoface onDeteleface) {
        this.onDeteleface = onDeteleface;
    }

    public void setOnUpdateface(OnMemoface onUpdateface) {
        this.onUpdateface = onUpdateface;
    }

    public void setOnLookface(OnMemoface onLookface) {
        this.onLookface = onLookface;
    }

    public LookAdapter(Context context, List<Account> list) {
        this.context = context;
        this.list = list;
    }

    public void clearList() {
        if (list != null) {
            list.clear();
            notifyDataSetInvalidated();
        }
    }

    public void setList(List<Account> list) {
        this.list = list;
        notifyDataSetChanged();
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
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_record, null, true);
            mHolder.llayout = (LinearLayout) convertView
                    .findViewById(R.id.layout);
            mHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            mHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            mHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            mHolder.tvRemark = (TextView) convertView
                    .findViewById(R.id.tvRemark);

            mHolder.btnLook = (Button) convertView.findViewById(R.id.btnLook);
            mHolder.btnUpdate = (Button) convertView
                    .findViewById(R.id.btnUpdate);
            mHolder.btnDelete = (Button) convertView
                    .findViewById(R.id.btnDelete);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.tvTitle.setText(list.get(position).getType());
        mHolder.tvPrice.setText(list.get(position).getPrice());
        mHolder.tvDate.setText(list.get(position).getDate());
        mHolder.tvRemark.setText(list.get(position).getRemark());
        mHolder.btnLook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (onLookface != null) {
                    onLookface.setPosition(list.get(position).getId());
                }
            }
        });

        mHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (onUpdateface != null) {
                    onUpdateface.setPosition(list.get(position).getId());
                }
            }
        });

        mHolder.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (onDeteleface != null) {
                    onDeteleface.setPosition(list.get(position).getId());
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvDate;
        private TextView tvRemark;
        private Button btnLook;
        private Button btnUpdate;
        private Button btnDelete;
        private LinearLayout llayout;
    }

    public interface OnMemoface {
        void setPosition(int id);
    }

}
