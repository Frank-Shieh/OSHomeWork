package com.example.frank_shieh.osdesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.List;
import java.util.Map;

/**
 * Created by FRANK_SHIEH on 2015/9/3.
 */
public class MyAdpter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdpter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     */
    public final class Module{
        public TextView sign;
        public TextView status;
        public TextView priorityNumber;
        public TextView totalTime;
        public TextView remainingTime;
        public NumberProgressBar numberProgressBar;

    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     Module module;
        if(convertView == null){
            module=new Module();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.list_item_view, null);
            module.sign=(TextView)convertView.findViewById(R.id.sign);
            module.status=(TextView)convertView.findViewById(R.id.status);
            module.priorityNumber=(TextView)convertView.findViewById(R.id.priorityNumber);
            module.totalTime=(TextView)convertView.findViewById(R.id.totalTime);
            module.remainingTime=(TextView)convertView.findViewById(R.id.remainingTime);
            module.numberProgressBar=(NumberProgressBar)convertView.findViewById(R.id.number_progress_bar);
            convertView.setTag(module);

        }else {
            module=(Module)convertView.getTag();

        }
        module.sign.setText((String)data.get(position).get("sign"));
        module.status.setText((String)data.get(position).get("status"));
        module.priorityNumber.setText((String)data.get(position).get("priorityNumber"));
        module.totalTime.setText((String)data.get(position).get("totalTime"));
        module.remainingTime.setText((String) data.get(position).get("remainingTime"));
        module.numberProgressBar.setProgress((Integer)data.get(position).get("numberProgressBar"));
        return convertView;
    }
}
