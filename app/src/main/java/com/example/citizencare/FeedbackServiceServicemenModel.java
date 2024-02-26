package com.example.citizencare;

public class FeedbackServiceServicemenModel {
    String UserID,ServiceType,FeedBackDescription;
    Integer FeedBackStars;
    FeedbackServiceServicemenModel()
    {

    }

    public FeedbackServiceServicemenModel(String serviceType, String userID) {
        UserID=userID;
        ServiceType = serviceType;
        this.FeedBackDescription = "None";
        this.FeedBackStars = 0;
    }
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
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
