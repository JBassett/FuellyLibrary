package com.ssvnormandy.fuellylibrary;

import android.os.AsyncTask;

import com.ssvnormandy.fuellylibrary.asynctasks.AuthenticateTask;

import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jbass on 3/6/15.
 */
public class Fuelly {

    private static Map<String, Fuelly> instances;
    public static Fuelly getInstance(String username){
        if(instances == null)
            instances = new HashMap<>();

        Fuelly instance;
        if((instance = instances.get(username)) == null){
            instances.put(username, instance = new Fuelly(username));
        }

        return instance;
    }

    private String username;
    private String password;

    private Fuelly(String username){
        this.username = username;
    }

    public AsyncTask<String, Void, Boolean> authenticate(String password){
        this.password = password;

        AuthenticateTask task = new AuthenticateTask();
        task.execute(username, password);

        return task;
    }

}
