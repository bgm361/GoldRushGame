package org.example.goldrushgame.oyuncular;

import org.example.goldrushgame.Kare;
import org.example.goldrushgame.Oyuncu;
import org.example.goldrushgame.Tahta;
import java.util.List;

public class DOyuncusu extends Oyuncu {

    public DOyuncusu(String ad, int baslangicAltini) {
        super(ad, baslangicAltini);
        this.x = 19;
        this.y = 19;
    }

    // D oyuncusu diğer oyuncuların listesine ihtiyaç duyar
    public Kare hedefBelirle(Tahta tahta, List<Oyuncu> digerOyuncular) {
        Kare enIyiHedef = null;
        double maxKar = -Double.MAX_VALUE;

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Kare kare = tahta.getKare(i, j);

                if (kare.isAltinVarMi() && !kare.isGizliMi()) {
                    int mesafe = Math.abs(this.x - i) + Math.abs(this.y - j);
                    int kar = kare.getAltinMiktari() - (mesafe * 5);

                    // Sinsi Mantık: Eğer bu altın bir rakibin hedefiyse ve ben daha uzaktaysam, başka hedefe bak.
                    // Şimdilik temel kâr hesabını yapalım, ileride "rakip takibi" metodunu içine ekleriz.
                    if (kar > maxKar) {
                        maxKar = kar;
                        enIyiHedef = kare;
                    }
                }
            }
        }
        return enIyiHedef;
    }

    // Not: Oyuncu sınıfındaki abstract metodu doldurmak zorundayız
    @Override
    public Kare hedefBelirle(Tahta tahta) {
        return hedefBelirle(tahta, null);
    }
}