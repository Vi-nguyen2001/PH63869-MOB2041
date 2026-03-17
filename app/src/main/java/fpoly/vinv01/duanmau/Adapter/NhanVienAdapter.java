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

import fpoly.vinv01.duanmau.Model.NhanVien;
import fpoly.vinv01.duanmau.R;

public class NhanVienAdapter extends BaseAdapter {
    private Context context;
    private List<NhanVien> list;
    private NhanVienItemListener listener;
    private DecimalFormat formatter = new DecimalFormat("###,###,### đ");

    public interface NhanVienItemListener {
        void onEdit(NhanVien nv);
        void onDelete(NhanVien nv);
    }

    public NhanVienAdapter(Context context, List<NhanVien> list, NhanVienItemListener listener) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nhan_vien, parent, false);
            holder = new ViewHolder();
            holder.tvMaNV = convertView.findViewById(R.id.tvMaNV);
            holder.tvHoTen = convertView.findViewById(R.id.tvHoTen);
            holder.tvChucVu = convertView.findViewById(R.id.tvChucVu);
            holder.tvLuong = convertView.findViewById(R.id.tvLuong);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NhanVien nv = list.get(position);
        if (nv != null) {
            holder.tvMaNV.setText(nv.getMaNV());
            holder.tvHoTen.setText(nv.getHoTen());
            holder.tvChucVu.setText(nv.getChucVu());
            holder.tvLuong.setText(formatter.format(nv.getLuong()));

            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(nv);
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(nv);
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvMaNV, tvHoTen, tvChucVu, tvLuong;
        ImageView btnEdit, btnDelete;
    }
}
