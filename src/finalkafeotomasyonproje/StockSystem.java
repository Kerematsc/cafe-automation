package finalkafeotomasyonproje;

import java.util.*;

public class StockSystem {
    private Map<Integer, Product> stock = new HashMap<>();

    public StockSystem() {
        stock.put(1, new Product(1, "Çay", 5.0, 200));
        stock.put(2, new Product(2, "Kahve", 10.0, 100));
        stock.put(3, new Product(3, "Pasta", 20.0, 50));
        stock.put(4, new Product(4, "Su", 3.0, 300));
        stock.put(5, new Product(5, "Börek", 15.0, 80));
    }

    public void listProducts() {
        System.out.println("Ürün Listesi:");
        for (Product p : stock.values()) {
            System.out.println(p.id + " - " + p.name + " (" + p.quantity + " adet) - " + p.price + "₺");
        }
    }

    public Collection<Product> getAllProducts() {
        return stock.values();
    }

    public Product getProduct(int id) {
        return stock.get(id);
    }

    public boolean isAvailable(int id, int qty) {
        Product p = stock.get(id);
        return p != null && p.quantity >= qty;
    }

    public void reduceStock(int id, int qty) {
        Product p = stock.get(id);
        if (p != null) {
            p.quantity -= qty;
        }
    }

    public void addProduct(Product p) {
        stock.put(p.id, p);
    }

    public void removeProduct(int id) {
        stock.remove(id);
    }

    public String getProductListAsText() {
        StringBuilder sb = new StringBuilder();
        for (Product p : stock.values()) {
            sb.append(p.id).append(" - ").append(p.name)
              .append(" (").append(p.quantity).append(" adet) - ")
              .append(p.price).append("₺\n");
        }
        return sb.toString();
    }
}
