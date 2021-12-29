package com.example.mixer2.mixer;


public class Mixtures {
    private double[] mixtures = new double[2];

    public Mixtures(double volume1, double volume2) {
        mixtures[0] = volume1;
        mixtures[1] = volume2;
    }

    public double getVolume(int position) {
        return mixtures[position];
    }
}
