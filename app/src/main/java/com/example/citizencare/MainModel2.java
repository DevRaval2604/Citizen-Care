package com.example.citizencare;

public class MainModel2 {
    String id,Address,ComplaintType,Date,Description,FeedBackDescription,Image,Latitude,Longitude,ServiceManID,Status,UserID,ResolutionDate;
    Integer FeedBackStars;
    Boolean ReportsGenerated;

    MainModel2()
    {

    }

    public MainModel2(String id, String address, String complaintType, String date, String description, String image, String latitude, String longitude, String userID) {
        this.id = id;
        Address = address;
        ComplaintType = complaintType;
        Date = date;
        Description = description;
        this.FeedBackDescription = "None";
        Image = image;
        Latitude = latitude;
        Longitude = longitude;
        this.ServiceManID = "None";
        this.Status = "Pending";
        UserID = userID;
        this.FeedBackStars = 0;
        this.ReportsGenerated=false;
        this.ResolutionDate="None";
    }

    public String getResolutionDate() {
        return ResolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        ResolutionDate = resolutionDate;
    }

    public Boolean getReportsGenerated() {
        return ReportsGenerated;
    }

    public void setReportsGenerated(Boolean reportsGenerated) {
        ReportsGenerated = reportsGenerated;
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

    public String getComplaintType() {
        return ComplaintType;
    }

    public void setComplaintType(String complaintType) {
        ComplaintType = complaintType;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
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
