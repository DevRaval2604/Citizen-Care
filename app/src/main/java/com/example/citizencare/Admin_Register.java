package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admin_Register extends AppCompatActivity {

    private EditText editTextRegisterFirstName, editTextRegisterMiddleName, editTextRegisterLastName, editTextRegisterEmail, editTextRegisterMobile, editTextRegisterPwd, editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private static final String TAG = "Register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFirstName = findViewById(R.id.editText_register_first_name);
        editTextRegisterMiddleName = findViewById(R.id.editText_register_middle_name);
        editTextRegisterLastName = findViewById(R.id.editText_register_last_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_confirm_password);

        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);


        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(view -> {
            int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
            radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

            //obtain the entered data
            String textFirstName = editTextRegisterFirstName.getText().toString();
            String textMiddleName = editTextRegisterMiddleName.getText().toString();
            String textLastName = editTextRegisterLastName.getText().toString();
            String textEmail = editTextRegisterEmail.getText().toString();
            String textMobile = editTextRegisterMobile.getText().toString();
            String textPwd = editTextRegisterPwd.getText().toString();
            String textConfirmPwd = editTextRegisterConfirmPwd.getText().toString();
            String textGender; //cant obtain value before verifying if any button was selected or not

            //validate mobile number using matcher and pattern(regular expression)
            String mobileRegex = "[6-9][0-9]{9}"; //First no. can be {6,7,8,9} and rest 9 nos. can be any no
            Matcher mobileMatcher;
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            mobileMatcher = mobilePattern.matcher(textMobile);

            if (TextUtils.isEmpty(textFirstName)) {
                Toast.makeText(Admin_Register.this, "Please enter your first name", Toast.LENGTH_LONG).show();
                editTextRegisterFirstName.setError("First Name is required");
                editTextRegisterFirstName.requestFocus();
            } else if (TextUtils.isEmpty(textMiddleName)) {
                Toast.makeText(Admin_Register.this, "Please enter your middle name", Toast.LENGTH_LONG).show();
                editTextRegisterMiddleName.setError("Middle Name is required");
                editTextRegisterMiddleName.requestFocus();
            } else if (TextUtils.isEmpty(textLastName)) {
                Toast.makeText(Admin_Register.this, "Please enter your last name", Toast.LENGTH_LONG).show();
                editTextRegisterLastName.setError("Last Name is required");
                editTextRegisterLastName.requestFocus();
            } else if (TextUtils.isEmpty(textEmail)) {
                Toast.makeText(Admin_Register.this, "Please enter your email", Toast.LENGTH_LONG).show();
                editTextRegisterEmail.setError("Email is required");
                editTextRegisterEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                Toast.makeText(Admin_Register.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                editTextRegisterEmail.setError("Valid email is required");
                editTextRegisterEmail.requestFocus();
            } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                Toast.makeText(Admin_Register.this, "Please select your gender", Toast.LENGTH_LONG).show();
                radioButtonRegisterGenderSelected.setError("Gender is required");
                radioButtonRegisterGenderSelected.requestFocus();
            } else if (TextUtils.isEmpty(textMobile)) {
                Toast.makeText(Admin_Register.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
                editTextRegisterMobile.setError("Mobile Number is required");
                editTextRegisterMobile.requestFocus();
            } else if (textMobile.length() != 10) {
                Toast.makeText(Admin_Register.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
                editTextRegisterMobile.setError("Mobile Number should be of 10 digits");
                editTextRegisterMobile.requestFocus();
            } else if (!mobileMatcher.find()) {
                Toast.makeText(Admin_Register.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
                editTextRegisterMobile.setError("Mobile Number is not valid");
                editTextRegisterMobile.requestFocus();
            } else if (TextUtils.isEmpty(textPwd)) {
                Toast.makeText(Admin_Register.this, "Please enter your password", Toast.LENGTH_LONG).show();
                editTextRegisterPwd.setError("Password is required");
                editTextRegisterPwd.requestFocus();
            } else if (textPwd.length() < 6) {
                Toast.makeText(Admin_Register.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                editTextRegisterPwd.setError("Password too weak");
                editTextRegisterPwd.requestFocus();
            } else if (TextUtils.isEmpty(textConfirmPwd)) {
                Toast.makeText(Admin_Register.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                editTextRegisterConfirmPwd.setError("Password Confirmation is required");
                editTextRegisterConfirmPwd.requestFocus();
            } else if (!textPwd.equals(textConfirmPwd)) {
                Toast.makeText(Admin_Register.this, "Please enter same password", Toast.LENGTH_LONG).show();
                editTextRegisterConfirmPwd.setError("Password Confirmation is required");
                editTextRegisterConfirmPwd.requestFocus();
                //clear the entered passwords
                editTextRegisterPwd.clearComposingText();
                editTextRegisterConfirmPwd.clearComposingText();
            } else {
                textGender = radioButtonRegisterGenderSelected.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                registerUser(textFirstName, textMiddleName, textLastName, textEmail, textGender, textMobile, textPwd);
            }
        });

        //show hide password using eye icon

        ImageView imageViewShowHidePwd = findViewById(R.id.imageview_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(view -> {
            if (editTextRegisterPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if pwd visible then hide it
                editTextRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //change icon
                imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
            } else {
                editTextRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
            }
        });

        //show hide confirm password using eye icon

        ImageView imageViewShowHidePwd1 = findViewById(R.id.imageview_show_hide_pwd1);
        imageViewShowHidePwd1.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd1.setOnClickListener(view -> {
            if (editTextRegisterConfirmPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if pwd visible then hide it
                editTextRegisterConfirmPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //change icon
                imageViewShowHidePwd1.setImageResource(R.drawable.ic_hide_pwd);
            } else {
                editTextRegisterConfirmPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewShowHidePwd1.setImageResource(R.drawable.ic_show_pwd);
            }
        });

    }
    private void registerUser(String textFirstName, String textMiddleName, String textLastName, String textEmail, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(Admin_Register.this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser =auth.getCurrentUser();
                //Enter User Data into Firebase Realtime Database.
                ReadWriteCitizenDetails WriteCitizenDetails=new ReadWriteCitizenDetails(textFirstName,textMiddleName,textLastName,textEmail,textGender,textMobile,textPwd,"Admin");
                //Extracting users reference from Database for "Users"
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                reference.child(Objects.requireNonNull(firebaseUser).getUid()).setValue(WriteCitizenDetails).addOnCompleteListener(task1 -> {
                    if (task.isSuccessful()){
                        //Send verification email
                        //firebaseUser.sendEmailVerification();
                        Toast.makeText(Admin_Register.this, "User registered successfully", Toast.LENGTH_LONG).show();
                        //Open User Profile after successful registration
                        Intent intent=new Intent(Admin_Register.this, Admin_Register.class);
                        //To prevent user from returning back to Register Activity on pressing back button after registration
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();//To close register activity
                    }else {
                        Toast.makeText(Admin_Register.this, "User registered failed. Please try again", Toast.LENGTH_LONG).show();
                    }
                    //Hide ProgressBar whether User creation is successful or failed
                    progressBar.setVisibility(View.GONE);
                });
            }
            else {
                try {
                    throw Objects.requireNonNull(task.getException());
                }
                catch (FirebaseAuthWeakPasswordException e) {
                    editTextRegisterPwd.setError("Your password is too weak. Kindly use a mix of alphabets, numbers and special characters.");
                    editTextRegisterPwd.requestFocus();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    editTextRegisterEmail.setError("Your email is invalid. Kindly re-enter.");
                    editTextRegisterEmail.requestFocus();
                } catch (FirebaseAuthUserCollisionException e) {
                    editTextRegisterEmail.setError("User is already registered with this email. Use another email.");
                    editTextRegisterEmail.requestFocus();
                } catch (Exception e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(Admin_Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                //Hide ProgressBar whether User creation is successful or failed
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}