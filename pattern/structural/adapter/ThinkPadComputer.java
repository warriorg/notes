package me.warriorg.design.structural.adapter;

public class ThinkPadComputer implements Computer {
    @Override
    public String readSD(SDCard sdCard) {
        if(sdCard == null)throw new NullPointerException("sd card null");
        return sdCard.readSD();
    }
}
