package org.example.goldrushgame;

import org.example.goldrushgame.oyuncular.AOyuncusu;
import org.example.goldrushgame.oyuncular.BOyuncusu;
import org.example.goldrushgame.oyuncular.COyuncusu;
import org.example.goldrushgame.oyuncular.DOyuncusu;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OyunEkrani extends JFrame {
    private JButton[][] butonlar;
    private Tahta tahta;
    private List<Oyuncu> oyuncuListesi;

    public OyunEkrani(Tahta tahta, List<Oyuncu> oyuncular) {
        this.tahta = tahta;
        this.oyuncuListesi = oyuncular;
        this.butonlar = new JButton[20][20];

        setTitle("Altın Toplama Oyunu");
        setSize(850, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel oyunAlani = new JPanel(new GridLayout(20, 20));
        add(oyunAlani, BorderLayout.CENTER);

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                butonlar[i][j] = new JButton();
                oyunAlani.add(butonlar[i][j]);
            }
        }

        // Oyunu başlatan buton
        JButton baslatButon = new JButton("Sonraki Tur");
        baslatButon.addActionListener(e -> sonrakiTur());
        add(baslatButon, BorderLayout.SOUTH);

        ekraniGuncelle();
        setVisible(true);
    }

    private void sonrakiTur() {
        boolean herhangiBiriHareketEttiMi = false;

        for (Oyuncu o : oyuncuListesi) {
            // 1. KASASINDA ALTIN VAR MI?
            // Bir oyuncunun en azından hedef belirleme maliyeti kadar altını olmalı
            int minMaliyet = (o instanceof AOyuncusu) ? 5 : (o instanceof BOyuncusu) ? 10 : (o instanceof COyuncusu) ? 15 : 20;

            if (o.kasa < minMaliyet) {
                continue; // Bu oyuncu artık hamle yapamaz, iflas etti.
            }


            // 2. HEDEF BELİRLE VE MALİYETİ DÜŞ
            Kare hedef = o.hedefBelirle(tahta);
            if (hedef != null) {
                o.kasa -= minMaliyet;
                o.harcananAltin += minMaliyet;
                o.logYaz("Hedef belirlendi. Maliyet: " + minMaliyet + ". Kalan Kasa: " + o.kasa);

                herhangiBiriHareketEttiMi = true; // En az bir kişi hareket edebiliyor

                int eskiX = o.x;
                int eskiY = o.y;

                // 3. HAREKET ET VE YOL MALİYETİNİ DÜŞ
                o.hareketEt(hedef);
                int atilanAdim = Math.abs(o.x - eskiX) + Math.abs(o.y - eskiY);
                int hareketMaliyeti = atilanAdim * 2;

                o.kasa -= hareketMaliyeti;
                o.harcananAltin += hareketMaliyeti;
                o.logYaz("Hareket edildi. Adım: " + atilanAdim + ". Yol Maliyeti: " + hareketMaliyeti);

                // 4. ALTIN TOPLANDI MI?
                if (o.x == hedef.getX() && o.y == hedef.getY()) {
                    o.kasa += hedef.getAltinMiktari();
                    o.toplananAltin += hedef.getAltinMiktari();
                    o.logYaz("ALTIN TOPLANDI! Miktar: " + hedef.getAltinMiktari() + ". Yeni Kasa: " + o.kasa);
                    hedef.setAltinVarMi(false);
                    hedef.setAltinMiktari(0);
                }
            }
        }

        ekraniGuncelle();

        // 5. OYUN SONU KONTROLÜ
        // Eğer bu turda kimse hareket edemediyse veya tahtada hiç altın kalmadıysa
        if (!herhangiBiriHareketEttiMi || tahtadakiAltinSayisi() == 0) {
            oyunSonuRaporuGoster();
        }
    }

    // Tahtada altın kalıp kalmadığını kontrol eden yardımcı metot
    private int tahtadakiAltinSayisi() {
        int sayac = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (tahta.getKare(i, j).isAltinVarMi()) sayac++;
            }
        }
        return sayac;
    }

    private void oyunSonuRaporuGoster() {
        // 1. Pencereyi Oluştur
        JDialog raporDialog = new JDialog(this, "Oyun Bitti - İstatistik Raporu", true);
        raporDialog.setLayout(new BorderLayout());
        raporDialog.setSize(400, 500);
        raporDialog.setLocationRelativeTo(this);

        // 2. Rapor Metnini Hazırla
        StringBuilder rapor = new StringBuilder("--- OYUN BİTTİ ÖZET RAPOR ---\n\n");
        for (Oyuncu o : oyuncuListesi) {
            rapor.append("Oyuncu: ").append(o.ad).append("\n")
                    .append("- Toplam Adım: ").append(o.toplamAdim).append("\n")
                    .append("- Harcanan Altın: ").append(o.harcananAltin).append("\n")
                    .append("- Toplanan Altın: ").append(o.toplananAltin).append("\n")
                    .append("- Kalan Altın: ").append(Math.max(0, o.kasa)).append("\n")
                    .append("----------------------------------\n");
        }

        JTextArea textArea = new JTextArea(rapor.toString());
        textArea.setEditable(false);
        raporDialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // 3. Alt Panel ve Butonlar
        JPanel butonPaneli = new JPanel();

        // Kapat Butonu
        JButton btnKapat = new JButton("Kapat");
        btnKapat.addActionListener(e -> raporDialog.dispose());

        // YENİDEN BAŞLAT BUTONU
        JButton btnTekrar = new JButton("Yeniden Başlat");
        btnTekrar.setBackground(Color.GREEN);
        btnTekrar.addActionListener(e -> {
            raporDialog.dispose(); // Raporu kapat
            this.dispose();        // Mevcut oyunu kapat
            GoldRushGameApplication.main(new String[]{}); // Yeni oyunu aç
        });

        butonPaneli.add(btnKapat);
        butonPaneli.add(btnTekrar);
        raporDialog.add(butonPaneli, BorderLayout.SOUTH);

        raporDialog.setVisible(true);
    }

    public void ekraniGuncelle() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Kare kare = tahta.getKare(i, j);
                JButton btn = butonlar[i][j];
                btn.setText("");
                btn.setBackground(Color.WHITE);

                if (kare.isAltinVarMi()) {
                    btn.setBackground(kare.isGizliMi() ? Color.GRAY : Color.YELLOW);
                    btn.setText(kare.isGizliMi() ? "?" : String.valueOf(kare.getAltinMiktari()));
                }

                for (Oyuncu o : oyuncuListesi) {
                    if (o.x == i && o.y == j) {
                        btn.setText(o.ad);
                        btn.setForeground(Color.WHITE);
                        if (o instanceof AOyuncusu) btn.setBackground(Color.RED);
                        else if (o instanceof BOyuncusu) btn.setBackground(Color.BLUE);
                        else if (o instanceof COyuncusu) btn.setBackground(Color.GREEN);
                        else if (o instanceof DOyuncusu) btn.setBackground(Color.MAGENTA);
                    }
                }
            }
        }
    }
}