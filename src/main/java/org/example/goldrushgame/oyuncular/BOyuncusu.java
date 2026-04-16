package org.example.goldrushgame.oyuncular;

import org.example.goldrushgame.Kare;
import org.example.goldrushgame.Oyuncu;
import org.example.goldrushgame.Tahta;

public class BOyuncusu extends Oyuncu {

    public BOyuncusu(String ad, int baslangicAltini) {
        super(ad, baslangicAltini);
        this.x = 0;
        this.y = 19;
    }

    @Override
    public Kare hedefBelirle(Tahta tahta) {
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