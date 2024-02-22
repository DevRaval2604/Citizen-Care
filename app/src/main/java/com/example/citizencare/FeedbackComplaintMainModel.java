package com.example.citizencare;

public class FeedbackComplaintMainModel {
    String ComplaintType,FeedBackDescription;
    Integer FeedBackStars;
    FeedbackComplaintMainModel()
    {

    }

    public FeedbackComplaintMainModel(String complaintType, String feedBackDescription, Integer feedBackStars) {
        ComplaintType = complaintType;
        FeedBackDescription = feedBackDescription;
        FeedBackStars = feedBackStars;
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
