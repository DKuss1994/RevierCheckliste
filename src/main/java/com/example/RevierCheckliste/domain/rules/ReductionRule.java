package com.example.RevierCheckliste.domain.rules;

public class ReductionRule extends Rule {

    private int reduceValue;

    @Override
    public int apply(int standardValue){
        int result = standardValue - reduceValue;
        return Math.max(result, 0);
    }
}
