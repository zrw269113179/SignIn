package com.example.administrator.signin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-01-23 .
 */

public class NotArriveAdapter extends RecyclerView.Adapter<NotArriveAdapter.ViewHolder> {
    private List<NotArrive> mList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView title;
        TextView time;
        public ViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            title = (TextView)view.findViewById(R.id.title);
            time = (TextView)view.findViewById(R.id.time);
        }
    }
    public NotArriveAdapter(List<NotArrive> list){mList = list;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_arrive_item,parent,false);
        return new NotArriveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotArrive record = mList.get(position);
        holder.name.setText(record.getName());
        SimpleDateFormat formatter    =   new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
        Date indata = new Date(record.getTime());//获取当前时间
        holder.time.setText(formatter.format(indata));
        String str = record.getTitle();
        if (str.isEmpty()){
            str = "没有写明原因！";
        }
        holder.title.setText(str);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
