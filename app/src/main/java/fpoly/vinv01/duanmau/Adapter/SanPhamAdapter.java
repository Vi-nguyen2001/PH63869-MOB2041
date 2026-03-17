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

import fpoly.vinv01.duanmau.R;
import fpoly.vinv01.duanmau.Model.SanPham;

public class SanPhamAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> list;
    private SanPhamItemListener listener;
    private DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");

    public interface SanPhamItemListener {
        void onEdit(SanPham sp);
        void onDelete(SanPham sp);
    }

    public SanPhamAdapter(Context context, List<SanPham> list, SanPhamItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() { return list != null ? list.size() : 0; }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
            holder = new ViewHolder();
            holder.tvProductName = convertView.findViewById(R.id.tvProductName);
            holder.tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
            holder.tvProductInfo = convertView.findViewById(R.id.tvProductInfo);
            holder.btnEdit = convertView.findViewById(R.id.btnEditProduct);
            holder.btnDelete = convertView.findViewById(R.id.btnDeleteProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SanPham sp = list.get(position);
        if (sp != null) {
            holder.tvProductName.setText(sp.getTenSP());
            holder.tvProductPrice.setText("Giá: " + formatter.format(sp.getGiaBan()));
            holder.tvProductInfo.setText("SL: " + sp.getSoLuong() + " | ĐVT: " + sp.getDonViTinh() + " | Loại: " + sp.getTenDanhMuc());

            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(sp);
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(sp);
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductInfo;
        ImageView btnEdit, btnDelete;
    }
}