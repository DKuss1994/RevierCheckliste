package com.example.securitydispatch.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChecklistRequest {
    @JsonProperty
    private long shiftId;
    @JsonProperty
    private long securityObjectId;
    public ChecklistRequest(){}

    public ChecklistRequest(long shiftId, long securityObjectId){


        this.shiftId = shiftId;

        this.securityObjectId = securityObjectId;
    }

    public long getShiftId() {
        return shiftId;
    }

    public long getSecurityObjectId() {
        return securityObjectId;
    }

}
