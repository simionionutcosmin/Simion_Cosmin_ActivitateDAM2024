package com.example.seminar4;

import android.os.Parcel;
import android.os.Parcelable;

public class Insula implements Parcelable {

    private String nume;
    private double suprafata;
    private String localizare;
    private boolean locuita;

    public Insula(String nume, double suprafata, String localizare, boolean locuita) {
        this.nume = nume;
        this.suprafata = suprafata;
        this.localizare = localizare;
        this.locuita = locuita;
    }

    public Insula() {
        this.nume = "";
        this.suprafata = 0.0;
        this.localizare = "";
        this.locuita = false;
    }

    protected Insula(Parcel in) {
        nume = in.readString();
        suprafata = in.readDouble();
        localizare = in.readString();
        locuita = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeDouble(suprafata);
        dest.writeString(localizare);
        dest.writeByte((byte) (locuita ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Insula> CREATOR = new Creator<Insula>() {
        @Override
        public Insula createFromParcel(Parcel in) {
            return new Insula(in);
        }

        @Override
        public Insula[] newArray(int size) {
            return new Insula[size];
        }
    };

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(double suprafata) {
        this.suprafata = suprafata;
    }

    public String getLocalizare() {
        return localizare;
    }

    public void setLocalizare(String localizare) {
        this.localizare = localizare;
    }

    public boolean isLocuita() {
        return locuita;
    }

    public void setLocuita(boolean locuita) {
        this.locuita = locuita;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Insula{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", suprafata=").append(suprafata);
        sb.append(", localizare='").append(localizare).append('\'');
        sb.append(", locuita=").append(locuita);
        sb.append('}');
        return sb.toString();
    }
}