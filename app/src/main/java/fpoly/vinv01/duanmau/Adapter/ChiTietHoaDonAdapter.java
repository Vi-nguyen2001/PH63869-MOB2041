package fpoly.vinv01.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import fpoly.vinv01.duanmau.Model.HoaDonChiTiet;
import fpoly.vinv01.duanmau.R;

public class ChiTietHoaDonAdapter extends BaseAdapter {
    private Context context;
    private List<HoaDonChiTiet> list;
    private DecimalFormat formatter = new DecimalFormat("###,###,### đ");

    public ChiTietHoaDonAdapter(Context context, List<HoaDonChiTiet> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_hoa_don, parent, false);
            holder = new ViewHolder();
            holder.tvTenSP = convertView.findViewById(R.id.tvTenSP);
            holder.tvSoLuongGia = convertView.findViewById(R.id.tvSoLuongGia);
            holder.tvThanhTien = convertView.findViewById(R.id.tvThanhTien);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HoaDonChiTiet ct = list.get(position);
        if (ct != null) {
            holder.tvTenSP.setText(ct.getTenSP());
            holder.tvSoLuongGia.setText(ct.getSoLuong() + " x " + formatter.format(ct.getGiaLucBan()));
            int thanhTien = ct.getSoLuong() * ct.getGiaLucBan();
            holder.tvThanhTien.setText(formatter.format(thanhTien));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvTenSP, tvSoLuongGia, tvThanhTien;
    }
}
