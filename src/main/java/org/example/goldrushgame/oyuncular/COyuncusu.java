package org.example.goldrushgame.oyuncular;

import org.example.goldrushgame.Kare;
import org.example.goldrushgame.Oyuncu;
import org.example.goldrushgame.Tahta;

public class COyuncusu extends Oyuncu {

    public COyuncusu(String ad, int baslangicAltini) {
        super(ad, baslangicAltini);
        this.x = 19;
        this.y = 0;
    }

    @Override
    public Kare hedefBelirle(Tahta tahta) {
        // Önce çevresindeki gizli altınları aç
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (i >= 0 && i < 20 && j >= 0 && j < 20) {
                    tahta.getKare(i, j).setGizliMi(false);
                }
            }
        }

        // Sonra en kârlı altını seç
        Kare enKarliAltin = null;
        double maxKar = -Double.MAX_VALUE;

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Kare kare = tahta.getKare(i, j);
                if (kare.isAltinVarMi() && !kare.isGizliMi()) {
                    int mesafe = Math.abs(this.x - i) + Math.abs(this.y - j);
                    int kar = kare.getAltinMiktari() - (mesafe * 5);
                    if (kar > maxKar) {
                        maxKar = kar;
                        enKarliAltin = kare;
                    }
                }
            }
        }
        return enKarliAltin;
    }
}