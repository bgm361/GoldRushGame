package org.example.goldrushgame.oyuncular;

import org.example.goldrushgame.Kare;
import org.example.goldrushgame.Oyuncu;
import org.example.goldrushgame.Tahta;

public class AOyuncusu extends Oyuncu {

    public AOyuncusu(String ad, int baslangicAltini) {
        super(ad, baslangicAltini);
        // A oyuncusu genellikle sol üst köşeden başlar (isteğe bağlı)
        this.x = 0;
        this.y = 0;
    }

    @Override
    public Kare hedefBelirle(Tahta tahta) {
        Kare enYakinAltin = null;
        double minMesafe = Double.MAX_VALUE;

        // Tahtayı tara ve gizli olmayan en yakın altını bul
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Kare kare = tahta.getKare(i, j);

                // Sadece görünür altınlara bakabilir
                if (kare.isAltinVarMi() && !kare.isGizliMi()) {
                    double mesafe = Math.abs(this.x - i) + Math.abs(this.y - j);
                    if (mesafe < minMesafe) {
                        minMesafe = mesafe;
                        enYakinAltin = kare;
                    }
                }
            }
        }
        return enYakinAltin;
    }
}