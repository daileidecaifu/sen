package sen.wedding.com.weddingsen.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import me.iwf.photopicker.utils.PermissionsConstant;
import sen.wedding.com.weddingsen.main.activity.HotelShowActivity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by lorin on 17/8/16.
 */

public class PermissionUtil {

    public static boolean checkWriteStoragePermission(Activity context) {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            ActivityCompat.requestPermissions(context, PermissionsConstant.PERMISSIONS_EXTERNAL_WRITE,
                    PermissionsConstant.REQUEST_EXTERNAL_WRITE);
        }
        return writeStoragePermissionGranted;
    }
}
