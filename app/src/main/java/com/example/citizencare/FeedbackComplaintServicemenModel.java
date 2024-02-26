package com.example.citizencare;

public class FeedbackComplaintServicemenModel {
    String UserID,ComplaintType,FeedBackDescription;
    Integer FeedBackStars;
    FeedbackComplaintServicemenModel()
    {

    }

    public FeedbackComplaintServicemenModel(String complaintType, String userID) {
        UserID=userID;
        ComplaintType = complaintType;
        this.FeedBackDescription = "None";
        this.FeedBackStars = 0;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getComplaintType() {
        return ComplaintType;
    }

    public void setComplaintType(String complaintType) {
        ComplaintType = complaintType;
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
