package com.example.RevierCheckliste.domain.rules;

public class AdditionalRule extends Rule {

    private int addValue;

    @Override
    public int apply(int standardValue){
        return standardValue + addValue;
    }
}
