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

import fpoly.vinv01.duanmau.Model.HoaDon;
import fpoly.vinv01.duanmau.R;

public class HoaDonAdapter extends BaseAdapter {
    private Context context;
    private List<HoaDon> list;
    private HoaDonItemListener listener;
    private DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");

    public interface HoaDonItemListener {
        void onDelete(HoaDon hd);
        void onClick(HoaDon hd);
    }

    public HoaDonAdapter(Context context, List<HoaDon> list, HoaDonItemListener listener) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hoa_don, parent, false);
            holder = new ViewHolder();
            holder.tvMaHD = convertView.findViewById(R.id.tvMaHD);
            holder.tvNgayLap = convertView.findViewById(R.id.tvNgayLap);
            holder.tvTenNV = convertView.findViewById(R.id.tvTenNV);
            holder.tvTenKH = convertView.findViewById(R.id.tvTenKH);
            holder.tvTongTien = convertView.findViewById(R.id.tvTongTien);
            holder.btnDelete = convertView.findViewById(R.id.btnDeleteHD);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HoaDon hd = list.get(position);
        if (hd != null) {
            holder.tvMaHD.setText(hd.getMaHD());
            holder.tvNgayLap.setText(hd.getNgayLap());
            holder.tvTenNV.setText(hd.getTenNV());
            holder.tvTenKH.setText(hd.getTenKhachHang());
            holder.tvTongTien.setText(formatter.format(hd.getTongTien()));

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(hd);
            });

            convertView.setOnClickListener(v -> {
                if (listener != null) listener.onClick(hd);
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvMaHD, tvNgayLap, tvTenNV, tvTenKH, tvTongTien;
        ImageView btnDelete;
    }
}
