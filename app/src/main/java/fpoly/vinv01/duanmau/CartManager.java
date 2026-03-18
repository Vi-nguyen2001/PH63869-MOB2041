package fpoly.vinv01.duanmau;

import java.util.ArrayList;
import java.util.List;
import fpoly.vinv01.duanmau.Model.GioHang;

public class CartManager {
    // Danh sách static để lưu hàng xuyên suốt ứng dụng
    public static List<GioHang> listGioHang = new ArrayList<>();

    // Hàm thêm hàng vào giỏ logic: nếu trùng mã SP thì tăng số lượng, chưa có thì thêm mới
    public static void addToCart(GioHang item) {
        for (GioHang gh : listGioHang) {
            if (gh.getMaSP() == item.getMaSP()) {
                gh.setSoLuongMua(gh.getSoLuongMua() + item.getSoLuongMua());
                return;
            }
        }
        listGioHang.add(item);
    }
}
