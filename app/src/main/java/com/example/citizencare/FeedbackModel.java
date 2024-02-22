package com.example.citizencare;

public class FeedbackModel {
    String ServiceType,FeedBackDescription;
    Integer FeedBackStars;
    FeedbackModel()
    {

    }

    public FeedbackModel(String serviceType, String feedBackDescription) {
        ServiceType = serviceType;
        FeedBackDescription = feedBackDescription;
        this.FeedBackStars = 0;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getFeedBackDescription() {
        return FeedBackDescription;
    }

    public void setFeedBackDescription(String feedBackDescription) {
        FeedBackDescription = feedBackDescription;
    }

    public Integer getFeedBackStars() {
        return FeedBackStars;
    }

    public void setFeedBackStars(Integer feedBackStars) {
        FeedBackStars = feedBackStars;
    }
}
