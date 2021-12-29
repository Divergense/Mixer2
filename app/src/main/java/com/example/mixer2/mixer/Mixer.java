package com.example.mixer2.mixer;

import static com.example.mixer2.mixer.Utility.trickySwap;

public class Mixer {
    private double sourceConcentration1;
    private double sourceConcentration2;
    private double targetConcentration;

    public Mixer(double concentration1, double concentration2, double concentration) {
        if (concentration1 > concentration2) {
            concentration1 = trickySwap(concentration2, concentration2=concentration1);
        }

        sourceConcentration1 = concentration1;
        sourceConcentration2 = concentration2;
        targetConcentration = concentration;
    }

    private double getEta() {
        return (sourceConcentration2 - targetConcentration) / (targetConcentration - sourceConcentration1);
    }

    public Mixtures getMixtureVolumes(double targetVolume) {
        // volume2 corresponds to higher concentration i.e. to sourceConcentration2
        double eta = getEta();
        double volume2 = targetVolume / (1 + eta);
        double volume1 = targetVolume - volume2;
        return new Mixtures(volume1, volume2);
    }
}
