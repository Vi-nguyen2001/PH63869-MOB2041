package fpoly.vinv01.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import fpoly.vinv01.duanmau.Model.CustomerTop;
import fpoly.vinv01.duanmau.R;

public class TopCustomerAdapter extends BaseAdapter {
    private Context context;
    private List<CustomerTop> customerList;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,### VNĐ");

    public TopCustomerAdapter(Context context, List<CustomerTop> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @Override
    public int getCount() {
        return (customerList != null) ? customerList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_customer, parent, false);
            holder = new ViewHolder();
            holder.tvRank = convertView.findViewById(R.id.tvRank);
            holder.ivAvatar = convertView.findViewById(R.id.ivAvatar);
            holder.tvCustomerName = convertView.findViewById(R.id.tvCustomerName);
            holder.tvCustomerId = convertView.findViewById(R.id.tvCustomerId);
            holder.tvTotalSpent = convertView.findViewById(R.id.tvTotalSpent);
            holder.tvOrderCount = convertView.findViewById(R.id.tvOrderCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CustomerTop customer = customerList.get(position);
        if (customer != null) {
            holder.tvRank.setText(String.valueOf(customer.getRank()));
            holder.ivAvatar.setImageResource(customer.getImageResId());
            holder.tvCustomerName.setText(customer.getName());
            holder.tvCustomerId.setText("ID: KH" + String.format("%04d", customer.getId()));
            holder.tvTotalSpent.setText(decimalFormat.format(customer.getTotalSpent()));
            holder.tvOrderCount.setText(customer.getOrderCount() + " Đơn hàng");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvRank;
        ImageView ivAvatar;
        TextView tvCustomerName;
        TextView tvCustomerId;
        TextView tvTotalSpent;
        TextView tvOrderCount;
    }
}