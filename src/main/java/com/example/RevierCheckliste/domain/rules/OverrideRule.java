package com.example.RevierCheckliste.domain.rules;

public class OverrideRule extends Rule {

    private int overrideValue;

    @Override
    public int apply(int standardValue){
        return overrideValue;
    }
}
