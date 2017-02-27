package com.example.administrator.signin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.signin.R;
import com.example.administrator.signin.modul.NotArrive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017-01-23 .
 */

public class NotArriveAdapter extends RecyclerView.Adapter<NotArriveAdapter.ViewHolder> {
    private List<NotArrive> mList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView title;
        TextView time;
        TextView notArrive;
        public ViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            title = (TextView)view.findViewById(R.id.title);
            time = (TextView)view.findViewById(R.id.time);
            notArrive = (TextView)view.findViewById(R.id.not_arrive);
        }
    }
    public NotArriveAdapter(Context context,List<NotArrive> list){this.context = context;mList = list;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_arrive_item,parent,false);
        return new NotArriveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NotArrive record = mList.get(position);
        holder.name.setText(record.getName());
        SimpleDateFormat formatter    =   new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
        Date indata = new Date(record.getTime());//获取当前时间
        holder.time.setText(formatter.format(indata));
        String str = record.getTitle();
        if (str == null){
            str = "没有写明原因！";
        }else {
            if (str.isEmpty()) {
                str = "没有写明原因！";
            }
        }
        holder.title.setText(str);
        holder.notArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View conView = layoutInflater.inflate(R.layout.title, null);
                final EditText editText = (EditText)conView.findViewById(R.id.start_sign_in_id);
                builder.setView(conView);
                builder.setTitle("请输入缺勤原因");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String str = editText.getText().toString();
                        record.setTitle(str);

                        record.update(context, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                                holder.title.setText(str);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
