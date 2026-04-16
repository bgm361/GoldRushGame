package org.example.goldrushgame;

public class Kare {
    private int x; // Satır
    private int y; // Sütun
    private boolean altinVarMi;
    private boolean gizliMi;
    private int altinMiktari;

    public Kare(int x, int y) {
        this.x = x;
        this.y = y;
        this.altinVarMi = false;
        this.gizliMi = false;
        this.altinMiktari = 0;
    }

    // Getter ve Setter Metotları (Sağ tık -> Generate -> Getter and Setter diyerek de yapabilirsin)
    public int getX() { return x; }
    public int getY() { return y; }

    public boolean isAltinVarMi() { return altinVarMi; }
    public void setAltinVarMi(boolean altinVarMi) { this.altinVarMi = altinVarMi; }

    public boolean isGizliMi() { return gizliMi; }
    public void setGizliMi(boolean gizliMi) { this.gizliMi = gizliMi; }

    public int getAltinMiktari() { return altinMiktari; }
    public void setAltinMiktari(int altinMiktari) { this.altinMiktari = altinMiktari; }
}