package fpoly.vinv01.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fpoly.vinv01.duanmau.Model.DanhMuc;
import fpoly.vinv01.duanmau.R;

public class DanhMucAdapter extends BaseAdapter {
    private Context context;
    private List<DanhMuc> danhMucList;
    private DanhMucItemListener listener;

    public interface DanhMucItemListener {
        void onEdit(DanhMuc danhMuc);
        void onDelete(DanhMuc danhMuc);
    }

    public DanhMucAdapter(Context context, List<DanhMuc> danhMucList, DanhMucItemListener listener) {
        this.context = context;
        this.danhMucList = danhMucList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return (danhMucList != null) ? danhMucList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return danhMucList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_danh_muc, parent, false);
            holder = new ViewHolder();
            holder.tvMaDanhMuc = convertView.findViewById(R.id.tvMaDanhMuc);
            holder.tvTenDanhMuc = convertView.findViewById(R.id.tvTenDanhMuc);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DanhMuc danhMuc = danhMucList.get(position);
        if (danhMuc != null) {
            holder.tvMaDanhMuc.setText("Mã: DM" + String.format("%03d", danhMuc.getMaDanhMuc()));
            holder.tvTenDanhMuc.setText(danhMuc.getTenDanhMuc());

            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(danhMuc);
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(danhMuc);
            });
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvMaDanhMuc, tvTenDanhMuc;
        ImageView btnEdit, btnDelete;
    }
}