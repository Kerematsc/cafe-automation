package finalkafeotomasyonproje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AnaMenu extends JFrame {
    private StockSystem stock;
    private java.util.List<Bill> bills = new ArrayList<>();
    private Set<Integer> rezerveMasalar = new HashSet<>();
    private Map<Integer, JButton> masaButonlari = new HashMap<>();

    private JButton adisyonBtn;
    private JButton stokBtn;
    private JButton stokGuncelleBtn;
    private JButton rezerveBtn;
    private JButton kapanisBtn;
    private JLabel lmasa;
    private JLabel bahce;
    private JLabel mutfak;

    public AnaMenu() {
        setTitle("Kafe Otomasyon Sistemi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(null);

        stock = new StockSystem();

        adisyonBtn = new JButton("ADİSYON");
        adisyonBtn.setBounds(0, 0, 500, 50);
        adisyonBtn.addActionListener(e -> {
            double toplam = 0;
            for (Bill b : bills) {
                toplam += b.getTotal();
            }
            JOptionPane.showMessageDialog(this, "Günlük Toplam Kazanç: " + toplam + "₺");
        });
        add(adisyonBtn);

        stokBtn = new JButton("STOK");
        stokBtn.setBounds(500, 0, 500, 50);
        stokBtn.addActionListener(e -> {
            String stokBilgi = stock.getProductListAsText();
            JOptionPane.showMessageDialog(this, stokBilgi);
        });
        add(stokBtn);

        kapanisBtn = new JButton("KAPANIŞ RAPORU");
        kapanisBtn.setBounds(0, 50, 1000, 30);
        kapanisBtn.addActionListener(e -> {
            double toplam = 0;
            for (Bill b : bills) {
                toplam += b.getTotal();
            }
            JOptionPane.showMessageDialog(this, "Kapanış Raporu\nGünlük Toplam Kazanç: " + toplam + "₺");
        });
        add(kapanisBtn);

        stokGuncelleBtn = new JButton("STOK GÜNCELLE");
        stokGuncelleBtn.setBounds(230, 620, 200, 30);
        stokGuncelleBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Güncellenecek ürün ID:");
            try {
                int id = Integer.parseInt(idStr);
                Product urun = stock.getProduct(id);
                if (urun == null) {
                    JOptionPane.showMessageDialog(null, "Ürün bulunamadı!");
                    return;
                }
                String yeniFiyatStr = JOptionPane.showInputDialog("Yeni fiyat (Şu an: " + urun.price + "):");
                String yeniAdetStr = JOptionPane.showInputDialog("Yeni adet (Şu an: " + urun.quantity + "):");

                double yeniFiyat = Double.parseDouble(yeniFiyatStr);
                int yeniAdet = Integer.parseInt(yeniAdetStr);

                urun.price = yeniFiyat;
                urun.quantity = yeniAdet;

                JOptionPane.showMessageDialog(null, "Ürün güncellendi.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Hatalı giriş!");
            }
        });
        add(stokGuncelleBtn);

        rezerveBtn = new JButton("REZERVE MASA");
        rezerveBtn.setBounds(450, 620, 200, 30);
        rezerveBtn.addActionListener(e -> {
            String masaStr = JOptionPane.showInputDialog("Hangi masa rezerve edilsin? (ör: 101)");
            try {
                int masa = Integer.parseInt(masaStr);
                rezerveMasalar.add(masa);
                JButton btn = masaButonlari.get(masa);
                if (btn != null) {
                    btn.setBackground(Color.RED);
                    btn.setForeground(Color.WHITE);
                }
                JOptionPane.showMessageDialog(null, "Masa " + masa + " rezerve edildi.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Geçersiz giriş.");
            }
        });
        add(rezerveBtn);

        JButton rezervIptalBtn = new JButton("REZERVASYON İPTAL");
        rezervIptalBtn.setBounds(670, 620, 200, 30);
        rezervIptalBtn.addActionListener(e -> {
            String masaStr = JOptionPane.showInputDialog("Hangi masanın rezervasyonu iptal edilsin? (ör: 101)");
            try {
                int masa = Integer.parseInt(masaStr);
                if (rezerveMasalar.contains(masa)) {
                    rezerveMasalar.remove(masa);
                    JButton btn = masaButonlari.get(masa);
                    if (btn != null) {
                        btn.setBackground(null);
                        btn.setForeground(Color.BLACK);
                    }
                    JOptionPane.showMessageDialog(null, "Masa " + masa + " artık rezerve değil.");
                } else {
                    JOptionPane.showMessageDialog(null, "Bu masa zaten rezerve değil.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Geçersiz giriş.");
            }
        });
        add(rezervIptalBtn);

        lmasa = new JLabel("L MASA", SwingConstants.CENTER);
        lmasa.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lmasa.setBounds(20, 100, 120, 120);
        add(lmasa);

        bahce = new JLabel("BAHÇE");
        bahce.setBounds(30, 250, 100, 20);
        add(bahce);

        mutfak = new JLabel("MUTFAK / BAR", SwingConstants.CENTER);
        mutfak.setBounds(300, 330, 300, 30);
        add(mutfak);

        refreshMasaButonlari();

        setVisible(true);
    }

    private void refreshMasaButonlari() {
        int[] bahceMasalar = {101, 102, 103};
        for (int i = 0; i < bahceMasalar.length; i++) {
            JButton b = createMasaButton(bahceMasalar[i]);
            b.setBounds(30, 280 + i * 40, 100, 30);
            add(b);
        }

        int[] ucgen = {211, 221, 212, 222, 213, 223};
        for (int i = 0; i < ucgen.length; i++) {
            JButton b = createMasaButton(ucgen[i]);
            b.setBounds(200 + (i % 2) * 100, 130 + (i / 2) * 80, 80, 40);
            add(b);
        }

        int[] dikdortgen = {311, 321, 312, 322, 313, 323};
        for (int i = 0; i < dikdortgen.length; i++) {
            JButton b = createMasaButton(dikdortgen[i]);
            b.setBounds(400 + (i % 2) * 100, 130 + (i / 2) * 80, 80, 40);
            add(b);
        }

        int[] besgen = {411, 421, 412, 422, 413, 423};
        for (int i = 0; i < besgen.length; i++) {
            JButton b = createMasaButton(besgen[i]);
            b.setBounds(600 + (i % 2) * 100, 130 + (i / 2) * 80, 80, 40);
            add(b);
        }

        repaint();
        revalidate();
    }

    private JButton createMasaButton(int masaNo) {
        JButton btn = new JButton(String.valueOf(masaNo));

        if (rezerveMasalar.contains(masaNo)) {
            btn.setBackground(Color.RED);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(null);
            btn.setForeground(Color.BLACK);
        }

        masaButonlari.put(masaNo, btn);
        btn.addActionListener(e -> masaTiklandi(masaNo));
        return btn;
    }

    private void masaTiklandi(int masaNo) {
        JFrame secim = new JFrame("Masa " + masaNo);
        secim.setSize(400, 300);
        secim.setLayout(new GridLayout(0, 1));

        Bill bill = new Bill(masaNo);

        for (Product p : stock.getAllProducts()) {
            JButton urunBtn = new JButton(p.name + " - " + p.price + "₺");
            urunBtn.addActionListener(e -> {
                String miktarStr = JOptionPane.showInputDialog("Kaç adet?");
                try {
                    int miktar = Integer.parseInt(miktarStr);
                    if (stock.isAvailable(p.id, miktar)) {
                        bill.addProduct(p, miktar);
                        stock.reduceStock(p.id, miktar);
                        JOptionPane.showMessageDialog(null, "Eklendi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Stok yetersiz!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hatalı giriş");
                }
            });
            secim.add(urunBtn);
        }

        JButton yazdirBtn = new JButton("Adisyonu Yazdır");
        yazdirBtn.addActionListener(e -> {
            bill.print();
            bill.saveToFile();
            bills.add(bill);
        });

        secim.add(yazdirBtn);
        secim.setVisible(true);
    }

    public static void main(String[] args) {
        new AnaMenu();
    }
}
