package com.monicatifanyz.manyom;

import java.util.Date;

public class Pengeluaran {
    private String kategoriP;
    private Date tanggal;
    private Double totalP;


    public Pengeluaran() {
        this.kategoriP = kategoriP;
        this.tanggal = tanggal;
        this.totalP = totalP;

    }

    public String getKategoriP() {
        return kategoriP;
    }

    public void setKategoriP(String kategoriP) {
        this.kategoriP = kategoriP;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Double getTotalP() {
        return totalP;
    }

    public void setTotalP(Double totalP) {
        this.totalP = totalP;
    }


}
