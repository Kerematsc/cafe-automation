package finalkafeotomasyonproje;

import java.util.*;
import java.io.*;

public class Bill {
    private int tableId;
    private List<Product> items = new ArrayList<>();
    private double total = 0;

    public Bill(int tableId) {
        this.tableId = tableId;
    }

    public void addProduct(Product p, int qty) {
        Product newItem = new Product(p.id, p.name, p.price, qty);
        items.add(newItem);
        total += p.price * qty;
    }

    public void print() {
        System.out.println("Adisyon - Masa " + tableId);
        for (Product p : items) {
            System.out.println(p.name + " x" + p.quantity + " = " + (p.price * p.quantity) + "₺");
        }
        System.out.println("Toplam: " + total + "₺");
    }

    public void saveToFile() {
        try {
            FileWriter writer = new FileWriter("adisyon_masa_" + tableId + ".txt");
            writer.write("Masa: " + tableId + "\n");
            for (Product p : items) {
                writer.write(p.name + " x" + p.quantity + " = " + (p.price * p.quantity) + "₺\n");
            }
            writer.write("Toplam: " + total + "₺\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    public double getTotal() {
        return total;
    }
}
