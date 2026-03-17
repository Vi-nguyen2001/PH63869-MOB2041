package fpoly.vinv01.duanmau.Model;

public class ProductTop {
    private int rank;
    private int id;
    private String name;
    private int salesQuantity;
    private int imageResId;

    public ProductTop(int rank, int id, String name, int salesQuantity, int imageResId) {
        this.rank = rank;
        this.id = id;
        this.name = name;
        this.salesQuantity = salesQuantity;
        this.imageResId = imageResId;
    }

    public int getRank() { return rank; }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getSalesQuantity() { return salesQuantity; }
    public int getImageResId() { return imageResId; }
}