package com.example.citizencare;



public class ReadWriteCitizenDetails {
        public String FirstName,MiddleName,LastName,Email,Gender,MobileNumber,Role;

        //Constructor
        public ReadWriteCitizenDetails(){}

        public ReadWriteCitizenDetails(String textFirstName,String textMiddleName,String textLastName,String textEmail,String textGender,String textMobile,String Role){
            this.FirstName=textFirstName;
            this.MiddleName=textMiddleName;
            this.LastName=textLastName;
            this.Email=textEmail;
            this.Gender=textGender;
            this.MobileNumber=textMobile;
            this.Role=Role;
        }
}
