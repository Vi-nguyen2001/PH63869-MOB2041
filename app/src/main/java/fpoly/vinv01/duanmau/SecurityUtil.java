package fpoly.vinv01.duanmau;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {
    public static String hashPassword(String plainPassword) {
        // 1. Kiểm tra nếu người dùng chưa nhập gì thì giữ nguyên
        if (plainPassword == null || plainPassword.isEmpty()) {
            return plainPassword;
        }
        // 2. BCrypt tạo ra một nhúm "Muối" (Salt) với nội dung hoàn toàn ngẫu nhiên
        String chuoiMuoiNgauNhien = BCrypt.gensalt();
        // 3. Trộn "Mật khẩu chữ thường" và "Muối" vào hàm băm (máy xay)
        // Kết quả sẽ tạo ra một chuỗi cực kỳ phức tạp (Ví dụ: $2a$10$wN1zO...) chứa cả Mầm muối và mã băm
        String chuoiDaDuocBAM = BCrypt.hashpw(plainPassword, chuoiMuoiNgauNhien);
        // 4. Trả về món cháo đã xay để cất vào SQLite
        return chuoiDaDuocBAM;
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // 1. Chặn luôn nếu dữ liệu trống, tránh để App bị sập (lỗi Crash)
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            // Hàm checkpw sẽ tự động làm 3 việc ngầm:
            // Bước A: Tìm và rút cái "Muối" được giấu bên trong chuỗi từ Database (hashedPassword)
            // Bước B: Lấy Mật khẩu thô (plainPassword) đem trộn với "Muối" vừa lôi ra rồi đi xay nát
            // Bước C: Đối chiếu xem kết quả sau khi xay có giống hệt với phần đuôi của Database không
            boolean isKhopMatKhau = BCrypt.checkpw(plainPassword, hashedPassword);
            return isKhopMatKhau;
        } catch (Exception e) {
            // Rơi vào lỗi Exception khi chuỗi trong Database tự dưng không đúng chuẩn BCrypt 
            // (Ví dụ bạn gõ đại số 123456 trực tiếp ở công cụ xem DB khiến nó dịch muối bị thất bại)
            // Thì mặc định là cho Đăng nhập sai.
            return false;
        }
    }
}
