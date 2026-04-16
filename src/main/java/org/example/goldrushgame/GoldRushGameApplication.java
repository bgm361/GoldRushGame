package org.example.goldrushgame;

import org.example.goldrushgame.oyuncular.AOyuncusu;
import org.example.goldrushgame.oyuncular.BOyuncusu;
import org.example.goldrushgame.oyuncular.COyuncusu;
import org.example.goldrushgame.oyuncular.DOyuncusu;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GoldRushGameApplication {

    public static void main(String[] args) {
        Tahta oyunTahtasi = new Tahta(20, 20);

        List<Oyuncu> oyuncular = new ArrayList<>();
        oyuncular.add(new AOyuncusu("A", 500));
        oyuncular.add(new BOyuncusu("B", 500));
        oyuncular.add(new COyuncusu("C", 500));
        oyuncular.add(new DOyuncusu("D", 500));

        new OyunEkrani(oyunTahtasi, oyuncular);
    }
}