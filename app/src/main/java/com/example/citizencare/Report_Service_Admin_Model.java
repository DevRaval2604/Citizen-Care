package com.example.citizencare;

public class Report_Service_Admin_Model {
    String id,Address,ServiceType,Date,Description,FeedBackDescription,ServiceManID,Status,UserID,ResolutionDate;
    Integer FeedBackStars;

    Report_Service_Admin_Model()
    {

    }
    public Report_Service_Admin_Model(String id, String address, String serviceType, String date, String description, String userID) {
        this.id = id;
        Address = address;
        ServiceType = serviceType;
        Date = date;
        Description = description;
        this.FeedBackDescription = "None";
        this.ServiceManID = "None";
        this.Status = "Pending";
        UserID = userID;
        this.FeedBackStars = 0;
        this.ResolutionDate="None";
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getResolutionDate() {
        return ResolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        ResolutionDate = resolutionDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFeedBackDescription() {
        return FeedBackDescription;
    }

    public void setFeedBackDescription(String feedBackDescription) {
        FeedBackDescription = feedBackDescription;
    }

    public String getServiceManID() {
        return ServiceManID;
    }

    public void setServiceManID(String serviceManID) {
        ServiceManID = serviceManID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Integer getFeedBackStars() {
        return FeedBackStars;
    }

    public void setFeedBackStars(Integer feedBackStars) {
        FeedBackStars = feedBackStars;
    }
}
