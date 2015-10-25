package org.social.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    public static boolean isAdmin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("params", Context.MODE_PRIVATE);
        return preferences.getBoolean("isAdmin", false);
    }

    public static void setIsAdmin(Context context, boolean isAdmin){
        SharedPreferences preferences = context.getSharedPreferences("params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isAdmin", isAdmin);
        editor.commit();
    }

    public static boolean isUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences("params", Context.MODE_PRIVATE);
        return preferences.getBoolean("isUser", false);
    }

    public static void setIsUser(Context context, boolean isUser){
        SharedPreferences preferences = context.getSharedPreferences("params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isUser", isUser);
        editor.commit();
    }

    public static boolean isRejectShared(Context context){
        SharedPreferences preferences = context.getSharedPreferences("params", Context.MODE_PRIVATE);
        return preferences.getBoolean("isRejectShared", false);
    }

    public static void setIsRejectShared(Context context, boolean isRejectShared){
        SharedPreferences preferences = context.getSharedPreferences("params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isRejectShared", isRejectShared);
        editor.commit();
    }

    public static int getUserId(Context context){
        SharedPreferences preferences = context.getSharedPreferences("parameters",
                Context.MODE_PRIVATE);
        return preferences.getInt("UserId", -1);
    }

    public static void setUserId(Context context, int userId){
        SharedPreferences preferences = context.getSharedPreferences("parameters",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("UserId", userId);
        editor.commit();
    }

    public static void exitLogin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("parameters",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UserId");
        editor.remove("isUser");
        editor.remove("isAdmin");
        editor.remove("isRejectShared");
        editor.commit();
    }
}
