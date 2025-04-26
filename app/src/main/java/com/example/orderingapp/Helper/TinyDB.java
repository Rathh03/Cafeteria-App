package com.example.orderingapp.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
//import com.example.orderingapp.Model.ItemsModel; // Import ItemsModel

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class TinyDB {

    private SharedPreferences preferences;
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath = "";

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    // Method to check if external storage is writable
    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    // Method to check if external storage is readable
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    // Method to get an image from a file path
    public Bitmap getImage(String path) {
        Bitmap bitmapFromPath = null;
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapFromPath;
    }

    // Method to get the last saved image path
    public String getSavedImagePath() {
        return lastImagePath;
    }

    // Method to save an image with a folder and image name
    public String putImage(String theFolder, String theImageName, Bitmap theBitmap) {
        if (theFolder == null || theImageName == null || theBitmap == null)
            return null;

        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = setupFullPath(theImageName);

        if (!mFullPath.equals("")) {
            lastImagePath = mFullPath;
            saveBitmap(mFullPath, theBitmap);
        }

        return mFullPath;
    }

    // Method to save an image with a full path
    public boolean putImageWithFullPath(String fullPath, Bitmap theBitmap) {
        return !(fullPath == null || theBitmap == null) && saveBitmap(fullPath, theBitmap);
    }

    private String setupFullPath(String imageName) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), DEFAULT_APP_IMAGEDATA_DIRECTORY);

        if (isExternalStorageReadable() && isExternalStorageWritable() && !mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                Log.e("ERROR", "Failed to setup folder");
                return "";
            }
        }

        return mFolder.getPath() + '/' + imageName;
    }

    private boolean saveBitmap(String fullPath, Bitmap bitmap) {
        if (fullPath == null || bitmap == null)
            return false;

        boolean fileCreated = false;
        boolean bitmapCompressed = false;
        boolean streamClosed = false;

        File imageFile = new File(fullPath);

        if (imageFile.exists())
            if (!imageFile.delete())
                return false;

        try {
            fileCreated = imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            bitmapCompressed = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
            bitmapCompressed = false;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    streamClosed = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    streamClosed = false;
                }
            }
        }

        return (fileCreated && bitmapCompressed && streamClosed);
    }

    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
    }

    private void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
    }

    // Method to get a list of integers from SharedPreferences
    public ArrayList<Integer> getListInt(String key) {
        return getListFromSharedPreferences(key, Integer.class);
    }

    // Method to get a list of strings from SharedPreferences
    public ArrayList<String> getListString(String key) {
        return getListFromSharedPreferences(key, String.class);
    }

    // Method to get a list of long values from SharedPreferences
    public ArrayList<Long> getListLong(String key) {
        return getListFromSharedPreferences(key, Long.class);
    }

    // Method to get a list of boolean values from SharedPreferences
    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> myList = getListString(key);
        ArrayList<Boolean> newList = new ArrayList<Boolean>();

        for (String item : myList) {
            newList.add(item.equals("true"));
        }

        return newList;
    }

    // Generic method to retrieve a list from SharedPreferences
    public <T> ArrayList<T> getListFromSharedPreferences(String key, Class<T> type) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<T> newList = new ArrayList<T>();

        for (String item : arrayToList) {
            if (type == Integer.class) {
                newList.add(type.cast(Integer.parseInt(item)));
            } else if (type == Long.class) {
                newList.add(type.cast(Long.parseLong(item)));
            } else if (type == String.class) {
                newList.add(type.cast(item));
            }
        }

        return newList;
    }

    // Method to get an object from SharedPreferences using Gson
    public <T> T getObject(String key, Class<T> classOfT) {
        String json = preferences.getString(key, null); // Use preferences.getString here
        Object value = new Gson().fromJson(json, classOfT);
        if (value == null)
            throw new NullPointerException();
        return classOfT.cast(value);
    }

    // Method to save a list of objects to SharedPreferences
    public <T> void putListObject(String key, ArrayList<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        preferences.edit().putString(key, json).apply();
    }

    // Method to get a list of objects from SharedPreferences
    public <T> ArrayList<T> getListObject(String key, Class<T> clazz) {
        Gson gson = new Gson();
        String json = preferences.getString(key, null);

        if (json == null) return new ArrayList<>();

        try {
            T[] array = gson.fromJson(json, (Class<T[]>) java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            return new ArrayList<>(Arrays.asList(array));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void putInt(String key, int value) {
        checkForNullKey(key);
        preferences.edit().putInt(key, value).apply();
    }

    public void putString(String key, String value) {
        checkForNullKey(key);
        checkForNullValue(value);
        preferences.edit().putString(key, value).apply();
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
