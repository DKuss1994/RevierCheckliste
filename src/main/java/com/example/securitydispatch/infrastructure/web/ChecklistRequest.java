package com.example.securitydispatch.infrastructure.web;

public class ChecklistRequest {
    private long shiftId;
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
