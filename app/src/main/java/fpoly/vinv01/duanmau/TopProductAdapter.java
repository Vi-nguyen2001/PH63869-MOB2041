package fpoly.vinv01.duanmau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TopProductAdapter extends BaseAdapter {
    private Context context;
    private List<ProductTop> productList;

    public TopProductAdapter(Context context, List<ProductTop> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return (productList != null) ? productList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_top_product, parent, false);
            holder = new ViewHolder();
            holder.tvRank = convertView.findViewById(R.id.tvRank);
            holder.ivProduct = convertView.findViewById(R.id.ivProduct);
            holder.tvProductName = convertView.findViewById(R.id.tvProductName);
            holder.tvProductId = convertView.findViewById(R.id.tvProductId);
            holder.tvSalesQuantity = convertView.findViewById(R.id.tvSalesQuantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductTop product = productList.get(position);
        if (product != null) {
            holder.tvRank.setText(String.valueOf(product.getRank()));
            holder.ivProduct.setImageResource(product.getImageResId());
            holder.tvProductName.setText(product.getName());
            holder.tvProductId.setText("Mã: " + product.getId());
            holder.tvSalesQuantity.setText(String.format("%,d", product.getSalesQuantity()));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvRank;
        ImageView ivProduct;
        TextView tvProductName;
        TextView tvProductId;
        TextView tvSalesQuantity;
    }
}