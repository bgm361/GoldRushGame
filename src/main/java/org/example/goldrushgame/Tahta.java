package org.example.goldrushgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tahta {
    private int satir;
    private int sutun;
    private Kare[][] kareler;
    private Random random = new Random();

    public Tahta(int satir, int sutun) {
        this.satir = satir;
        this.sutun = sutun;
        this.kareler = new Kare[satir][sutun];
        tahtayiOlustur();
        altinlariDagit(20, 10); // %20 normal, %10 gizli altın
    }

    private void tahtayiOlustur() {
        for (int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                kareler[i][j] = new Kare(i, j);
            }
        }
    }

    private void altinlariDagit(int normalYuzde, int gizliYuzde) {
        int toplamKare = satir * sutun;
        int normalAltinSayisi = (toplamKare * normalYuzde) / 100;
        int gizliAltinSayisi = (toplamKare * gizliYuzde) / 100;

        // Tüm koordinatları bir liste olarak alalım (karıştırmak için)
        List<Integer> indexler = new ArrayList<>();
        for (int i = 0; i < toplamKare; i++) indexler.add(i);
        Collections.shuffle(indexler);

        int[] altinDegerleri = {5, 10, 15, 20}; // Altın miktarları

        // Önce Normal Altınları Yerleştir
        for (int i = 0; i < normalAltinSayisi; i++) {
            int idx = indexler.get(i);
            int r = idx / sutun;
            int c = idx % sutun;
            kareler[r][c].setAltinVarMi(true);
            kareler[r][c].setGizliMi(false);
            kareler[r][c].setAltinMiktari(altinDegerleri[random.nextInt(altinDegerleri.length)]);
        }

        // Sonra Gizli Altınları Yerleştir
        for (int i = normalAltinSayisi; i < (normalAltinSayisi + gizliAltinSayisi); i++) {
            int idx = indexler.get(i);
            int r = idx / sutun;
            int c = idx % sutun;
            kareler[r][c].setAltinVarMi(true);
            kareler[r][c].setGizliMi(true);
            kareler[r][c].setAltinMiktari(altinDegerleri[random.nextInt(altinDegerleri.length)]);
        }
    }

    public Kare getKare(int r, int c) {
        return kareler[r][c];
    }
}