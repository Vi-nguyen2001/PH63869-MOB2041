package fpoly.vinv01.duanmau;

public class CustomerTop {
    private int rank;
    private int id;
    private String name;
    private double totalSpent;
    private int orderCount;
    private int imageResId;

    public CustomerTop(int rank, int id, String name, double totalSpent, int orderCount, int imageResId) {
        this.rank = rank;
        this.id = id;
        this.name = name;
        this.totalSpent = totalSpent;
        this.orderCount = orderCount;
        this.imageResId = imageResId;
    }

    public int getRank() { return rank; }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getTotalSpent() { return totalSpent; }
    public int getOrderCount() { return orderCount; }
    public int getImageResId() { return imageResId; }
}