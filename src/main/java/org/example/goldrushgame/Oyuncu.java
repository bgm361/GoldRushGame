package org.example.goldrushgame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Oyuncu {
    public String ad;
    public int x, y;
    public int kasa;

    // İstatistikler için yeni değişkenler
    public int toplamAdim = 0;
    public int harcananAltin = 0;
    public int toplananAltin = 0;

    public Oyuncu(String ad, int baslangicAltini) {
        this.ad = ad;
        this.kasa = baslangicAltini;
        // Oyun başında log dosyasını temizle/oluştur
        // Oyuncu.java içindeki constructor kısmı
        try (PrintWriter pw = new PrintWriter(new FileWriter(ad + "_log.txt", false))) { // Burayı false yaptık
            pw.println("--- " + ad + " OYUNU BAŞLADI ---");
        } catch (IOException e) { e.printStackTrace(); } }

    public abstract Kare hedefBelirle(Tahta tahta);

    // Log yazma metodu
    public void logYaz(String mesaj) {
        try (FileWriter fw = new FileWriter(this.ad + "_log.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(mesaj);
            // BU SATIRI EKLE:
            System.out.println("LOG DOSYASINA YAZILDI: " + mesaj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hareketEt(Kare hedef) {
        if (hedef == null) return;
        for (int i = 0; i < 3; i++) {
            if (this.x < hedef.getX()) this.x++;
            else if (this.x > hedef.getX()) this.x--;
            else if (this.y < hedef.getY()) this.y++;
            else if (this.y > hedef.getY()) this.y--;

            this.toplamAdim++; // Her adımda istatistiği artır
            if (this.x == hedef.getX() && this.y == hedef.getY()) break;
        }
    }
}