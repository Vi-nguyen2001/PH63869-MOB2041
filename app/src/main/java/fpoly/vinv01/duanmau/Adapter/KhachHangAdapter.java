package fpoly.vinv01.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fpoly.vinv01.duanmau.Model.KhachHang;
import fpoly.vinv01.duanmau.R;

public class KhachHangAdapter extends BaseAdapter {
    private Context context;
    private List<KhachHang> list;
    private KhachHangItemListener listener;

    public interface KhachHangItemListener {
        void onEdit(KhachHang kh);
        void onDelete(KhachHang kh);
    }

    public KhachHangAdapter(Context context, List<KhachHang> list, KhachHangItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
            holder = new ViewHolder();
            holder.tvMaKH = convertView.findViewById(R.id.tvMaKH);
            holder.tvHoTenKH = convertView.findViewById(R.id.tvHoTenKH);
            holder.tvDienThoaiKH = convertView.findViewById(R.id.tvDienThoaiKH);
            holder.tvEmailKH = convertView.findViewById(R.id.tvEmailKH);
            holder.tvDiaChiKH = convertView.findViewById(R.id.tvDiaChiKH);
            holder.btnEdit = convertView.findViewById(R.id.btnEditKH);
            holder.btnDelete = convertView.findViewById(R.id.btnDeleteKH);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        KhachHang kh = list.get(position);
        if (kh != null) {
            holder.tvMaKH.setText(kh.getMaKH());
            holder.tvHoTenKH.setText(kh.getHoTen());
            holder.tvDienThoaiKH.setText("SĐT: " + kh.getDienThoai());
            holder.tvEmailKH.setText("Email: " + kh.getEmail());
            holder.tvDiaChiKH.setText("ĐC: " + kh.getDiaChi());

            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(kh);
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(kh);
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvMaKH, tvHoTenKH, tvDienThoaiKH, tvEmailKH, tvDiaChiKH;
        ImageView btnEdit, btnDelete;
    }
}
