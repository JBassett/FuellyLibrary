package com.ssvnormandy.fuellylibrary.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.io.IOException;

/**
 * Created by jbass on 3/7/15.
 */
public class AuthenticateTask extends AsyncTask<String, Void, Boolean> {
    private static String TAG = "AuthenticateTask";

    @Override
    protected Boolean doInBackground(String... params) {
        if(params.length != 2) {
            Log.e(TAG, "Cannon authenticate without 2 params.");
            return false;
        }
        Document doc;
        try {
            Log.d(TAG, "Trying to get login form.");
            doc = Jsoup.connect("https://m.fuelly.com/login").get();
            Log.d(TAG, "Successfully got login form.");
        } catch (IOException e) {
            Log.e(TAG, "Issue getting login form.", e);
            return false;
        }
        FormElement form = doc.getAllElements().forms().isEmpty() ? null : doc.getAllElements().forms().get(0);

        for (Connection.KeyVal keyVal : form.formData()) {

            if (keyVal.key().equals("email"))
                keyVal.value(params[0]);
            else if (keyVal.key().equals("password"))
                keyVal.value(params[1]);
            else if (keyVal.key().equals("rememberMe"))
                // Set to 0 aka true to keep session longer.
                keyVal.value("0");
        }

        try {
            doc = form.submit().post();
        } catch (IOException e) {
            Log.e(TAG, "Issue submitting form data.", e);
            return false;
        }

        return !doc.title().contains("Log In");
    }
}
