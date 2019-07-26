package com.gh_hitech.devicecontroller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.model.IBaseName;

import java.util.HashMap;
import java.util.List;

import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class CheckboxSelectAdapter extends BaseAdapter {
    /**
     * 用来控制CheckBox的选中状况
     */
    private static HashMap<Integer, Boolean> isSelected;
    /**
     * 填充数据的list
     */
    protected List<IBaseName> list;
    private Context context;
    /**
     * 用来导入布局
     */
    private LayoutInflater inflater = null;

    public CheckboxSelectAdapter(List<IBaseName> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<>();
        initDate();
    }


    /**
     * 初始化isSelected的数据
     */
    private void initDate() {
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, list.get(i).isCheck);
        }
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        CheckboxSelectAdapter.isSelected = isSelected;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertView
            //可以用CheckedTextView替代
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder.tv = convertView.findViewById(R.id.item_tv);
            holder.cb = convertView.findViewById(R.id.item_cb);
            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置list中TextView的显示
        holder.tv.setText(list.get(position).getIName() + "");
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public void putAllCheck(boolean toggle, ListView listView) {
        for (int i = 0; i < listView.getChildCount(); i++) {
            View view = listView.getChildAt(i);
            CheckboxSelectAdapter.ViewHolder holder = (CheckboxSelectAdapter.ViewHolder) view.getTag();
            holder.cb.setChecked(toggle);
            isSelected.put(i, holder.cb.isChecked());
            list.get(i).isCheck = holder.cb.isChecked();
        }
    }

    public void putInvertCheck(ListView listView) {
        for (int i = 0; i < listView.getChildCount(); i++) {
            View view = listView.getChildAt(i);
            CheckboxSelectAdapter.ViewHolder holder = (CheckboxSelectAdapter.ViewHolder) view.getTag();
            holder.cb.toggle();
            isSelected.put(i, holder.cb.isChecked());
            list.get(i).isCheck = holder.cb.isChecked();
        }
    }

    public void putIsCheck(int position, View view) {
        CheckboxSelectAdapter.ViewHolder holder = (CheckboxSelectAdapter.ViewHolder) view.getTag();
        holder.cb.toggle();
        isSelected.put(position, holder.cb.isChecked());
        list.get(position).isCheck = holder.cb.isChecked();
    }

    public void putSingleCheck(int position, View view) {
        CheckboxSelectAdapter.ViewHolder holder = (CheckboxSelectAdapter.ViewHolder) view.getTag();
        holder.cb.toggle();
        isSelected.put(position, holder.cb.isChecked());
        list.get(position).isCheck = holder.cb.isChecked();
        for (int i = 0; i < list.size(); i++) {
            if (position != i) {
                list.get(i).isCheck = false;
            }
        }
    }

    public String getCheckName(HashMap<Integer, Boolean> isSelected) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < isSelected.size(); i++) {
            if (isSelected.get(i)) {
                addSb(sb, list.get(i).getIName());
            }
        }
        return sb.toString();
    }

    /**
     * 对StringBuffer进行操作
     *
     * @param sb
     * @param content
     * @return
     */
    public StringBuffer addSb(StringBuffer sb, String content) {
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append(content);
        } else {
            sb = sb.append("," + content);
        }
        return sb;
    }

    public String getCheckId(HashMap<Integer, Boolean> isSelected) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < isSelected.size(); i++) {
            if (isSelected.get(i)) {
                addSb(sb, list.get(i).getIid());
            }
        }
        return sb.toString();
    }

    /**
     * 对StringBuffer进行操作
     *
     * @param sb
     * @param content
     * @return
     */
    public StringBuffer addSb(StringBuffer sb, Long content) {
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append(content);
        } else {
            sb = sb.append(",").append(content);
        }
        return sb;
    }

    public int getIsSelectSum() {
        int temp = 0;
        for (int i = 0; i < getIsSelected().size(); i++) {
            if (getIsSelected().get(i)) {
                temp++;
            }
        }
        return temp;
    }

    public static class ViewHolder {
        public TextView tv;
        public CheckBox cb;
    }

}
