package com.example.ce2006proj;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.EventListener;


public class CheckUser {
    static Context context;

    public static void CheckRegisterUser(String Username, String Password, UserAutenticationCallback userAutenticationCallback) {
//        // this method should do 2 things. One check is to check if the username is not found i the database
//        // the other is to check if it it is nonnull

        if(Username.isEmpty() | Password.isEmpty())
        {
            Log.e("username",Username);
            userAutenticationCallback.onResponse("Please enter a valid username/password");
        }
        else
        {
            UserDatabase.FetchUser(new FirebaseUserCallback() {

                @Override
                public void onResponse(User user) {

                    if(user!=null)
                    {

                        userAutenticationCallback.onResponse("user already exist");

                    }
                    else
                    {
                         UserDatabase.RegisterUser(new User(Username,Password));
                         userAutenticationCallback.onResponse("You have been successfully been registered\n " +
                                 "Please log in with your new credentials");
                    }
                }
            },Username);
            // anything outside the implemented function needs to be directed to a loading page
        }




    }

    public static void CheckLoginUser(String Username,String Password, UserAutenticationCallback userAutenticationCallback)
    {
        // get the username from the database
        // if the username is null display text message using the callback
        // if the username is not null then set the database user to this user
        // we can only check the password in the callback section of the mainapp

        if(Username.isEmpty() | Password.isEmpty())
        {
            Log.e("username",Username);
            userAutenticationCallback.onResponse("Please enter a valid username/password");
        }
        else
        {
            UserDatabase.FetchUser(new FirebaseUserCallback() {

                @Override
                public void onResponse(User user) {

                    if(user!=null)
                    {

                        if(user.getPassword().equals(Password) )
                        {
                            UserDatabase.user = user;
                            // at this point we need to get the children associated with that user
                            StudentDatabase.FetchChildren();
                            UserDatabase.FetchFavSchools();
                            userAutenticationCallback.onResponse("WELCOME ");
                            Intent i = new Intent(context, MainPage.class);
                            context.startActivity(i);


                        }
                        else
                        {
                            userAutenticationCallback.onResponse("Invalid Password");
                        }

                    }
                    else
                    {
                        userAutenticationCallback.onResponse("This Username does not exist\n" +
                                "Please try again");
                    }
                }
            },Username);
            // anything outside the implemented function needs to be directed to a loading page
        }
    }


}



