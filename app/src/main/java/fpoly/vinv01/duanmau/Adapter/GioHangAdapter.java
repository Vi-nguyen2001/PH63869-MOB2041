package fpoly.vinv01.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;
import fpoly.vinv01.duanmau.CartManager;
import fpoly.vinv01.duanmau.Model.GioHang;
import fpoly.vinv01.duanmau.R;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private List<GioHang> list;
    private GioHangListener listener;
    private DecimalFormat formatter = new DecimalFormat("###,###,### đ");

    public interface GioHangListener {
        void onQuantityChanged(); // Để Activity tính lại tổng tiền
    }

    public GioHangAdapter(Context context, List<GioHang> list, GioHangListener listener) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
            holder = new ViewHolder();
            holder.tvTen = convertView.findViewById(R.id.tvTenSPCart);
            holder.tvGia = convertView.findViewById(R.id.tvGiaSPCart);
            holder.tvSoLuong = convertView.findViewById(R.id.tvSoLuongCart);
            holder.btnPlus = convertView.findViewById(R.id.btnPlusCart);
            holder.btnMinus = convertView.findViewById(R.id.btnMinusCart);
            holder.btnRemove = convertView.findViewById(R.id.btnRemoveCart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GioHang gh = list.get(position);
        holder.tvTen.setText(gh.getTenSP());
        holder.tvGia.setText(formatter.format(gh.getGiaBan()));
        holder.tvSoLuong.setText(String.valueOf(gh.getSoLuongMua()));

        // Xử lý tăng số lượng
        holder.btnPlus.setOnClickListener(v -> {
            gh.setSoLuongMua(gh.getSoLuongMua() + 1);
            notifyDataSetChanged();
            if (listener != null) listener.onQuantityChanged();
        });

        // Xử lý giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            if (gh.getSoLuongMua() > 1) {
                gh.setSoLuongMua(gh.getSoLuongMua() - 1);
                notifyDataSetChanged();
                if (listener != null) listener.onQuantityChanged();
            }
        });

        // Xử lý xóa khỏi giỏ
        holder.btnRemove.setOnClickListener(v -> {
            CartManager.listGioHang.remove(position);
            notifyDataSetChanged();
            if (listener != null) listener.onQuantityChanged();
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvTen, tvGia, tvSoLuong;
        ImageButton btnPlus, btnMinus;
        android.widget.ImageView btnRemove;
    }
}
