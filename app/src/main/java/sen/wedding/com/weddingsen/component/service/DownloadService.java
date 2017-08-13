package sen.wedding.com.weddingsen.component.service;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import sen.wedding.com.weddingsen.R;

/**
 * @Author:Lorin
 * @Date 2015-1-18
 * @content 修改优化的单线程下载安装模块，默认同一时间只可启动一线程，且有30秒超时逻辑
 */
public class DownloadService extends Service {

    private boolean is_processing = false;//是否已进行标识
    private int progress; // 定义进度值
    private int timeout = 30;//最长耗时超过自动结束

    int handmsg = 1;//
    private static final int DOWN_UPDATE = 0;// 下载中消息
    private static final int DOWN_OVER = 1;// 下载完成消息
    private static final int DOWN_TIMEOUT = 2;// 下载中超时

    private final int NotificationID = 0x10000;
    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;

//    private HttpHandler<File> mDownLoadHelper;

    // 文件下载路径
    private String debug_APK_url = "http://gdown.baidu.com/data/wisegame/a5947fef7e036da5/MagicZither_7.apk";
    // 文件保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String debug_APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NotificationDemo";
    private String saveFileName = debug_APK_dir + "/NotificationDemo.apk";


    /**
     * Title: onBind
     *
     * @param intent
     * @return [url=home.php?mod=space&uid=133757]@see[/url] android.app.Service#onBind(android.content.Intent)
     * @Description:
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Title: onCreate
     *
     * @Description:
     * @see Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Title: onStartCommand
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     * @Description:
     * @see Service#onStartCommand(Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        // 接收Intent传来的参数:
//        APK_url = intent.getStringExtra("apk_url");

//        DownFile(APK_url, APK_dir + File.separator + "xxx");
//        initAPKDir();// 创建保存路径
        Toast.makeText(DownloadService.this, "start loading!", Toast.LENGTH_SHORT).show();
        if (!is_processing) {
            new Thread(mdownApkRunnable).start();
        } else {
            Toast.makeText(DownloadService.this, "Processing!!!", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * @Description:判断是否插入SD卡
     */
    private boolean isHasSdcard() {
        String status = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

//

    /**
     * @param x     当前值
     * @param total 总值
     *              [url=home.php?mod=space&uid=7300]@return[/url] 当前百分比
     * @Description:返回百分之值
     */
    private String getPercent(int x, int total) {
        String result = "";// 接受百分比的值
        double x_double = x * 1.0;
        double tempresult = x_double / total;
        // 百分比格式，后面不足2位的用0补齐 ##.00%
        DecimalFormat df1 = new DecimalFormat("0.00%");
        result = df1.format(tempresult);
        return result;
    }

    /**
     * @return
     * @Description:获取当前应用的名称
     */
    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * Title: onDestroy
     *
     * @Description:
     * @see Service#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }


    // 下载APK的线程匿名类START
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                is_processing = true;
                System.out.println("START_FLAG");
                Bitmap btm = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.login_icon);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.mipmap.login_icon);
//                builder.setLargeIcon(btm);
                builder.setContentTitle("Hello");
                builder.setTicker("It's ready!");
//                builder.setContentTitle(getApplicationName());
                builder.setContentText("XXX,请稍后...");
                builder.setNumber(0);
                builder.setProgress(100, 0, false);
                builder.setContentInfo(getPercent(0, 100));
                builder.setAutoCancel(true);
                mNotificationManager.notify(NotificationID, builder.build());
                System.out.println("NOTIFICATION_FLAG" + debug_APK_url);

                URL url = new URL(debug_APK_url);
                System.out.println("HTTPCONNECTION_FLAG1");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                System.out.println("HTTPCONNECTION_FLAG2");

                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(debug_APK_dir);
                Log.e("test", file.exists() + "");
                if (!file.exists()) {
                    Log.e("test1", file.exists() + "");
                    file.mkdirs();
                    Log.e("test2", file.exists() + "");
                }
                String apkFile = saveFileName;
                Log.e("test3", apkFile);
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];
                long start_time = System.currentTimeMillis();
                do {
                    if ((System.currentTimeMillis() - start_time) > timeout * 1000) {
//                        Toast.makeText(TestDownloadService.this, "Time Out!!!", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessage(DOWN_TIMEOUT);
                        break;

                    }
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    if (handmsg < progress) {
                        handmsg++;
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                    }
                    // 更新进度
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (true);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                is_processing = false;
            } catch (IOException e) {
                e.printStackTrace();
                is_processing = false;
                Log.e("test", e.getMessage());
            }
        }
    };

    // 处理下载进度的Handler Start
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    int x = Long.valueOf(handmsg).intValue();
                    int totalS = Long.valueOf(100).intValue();
                    builder.setProgress(totalS, x, false);
                    builder.setContentInfo(getPercent(x, totalS));
                    mNotificationManager.notify(NotificationID, builder.build());
                    super.handleMessage(msg);
                    break;
                case DOWN_OVER:
                    File apkfile = new File(saveFileName);
                    if (!apkfile.exists()) {
                        Toast.makeText(DownloadService.this, "要安装的文件不存在，请检查路径", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
//                    Uri uri = Uri.fromFile(new File(APK_dir));
                    installIntent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent mPendingIntent = PendingIntent.getActivity(DownloadService.this, 0, installIntent, 0);
                    builder.setContentText("下载完成,请点击安装");
                    builder.setContentIntent(mPendingIntent);
                    mNotificationManager.notify(NotificationID, builder.build());
                    // 震动提示
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000L);// 参数是震动时间(long类型)
                    stopSelf();
                    startActivity(installIntent);// 下载完成之后自动弹出安装界面
                    mNotificationManager.cancel(NotificationID);
                    is_processing = false;
                    break;
                case DOWN_TIMEOUT:
                    mNotificationManager.cancel(NotificationID);
                    Toast.makeText(DownloadService.this, "TIMEOUT!", Toast.LENGTH_SHORT).show();
                    is_processing = false;
                    break;
                default:
                    break;
            }
        }

        ;
    };


}
