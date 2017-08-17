package sen.wedding.com.weddingsen.utils.crash;

import android.content.Context;
import android.os.Looper;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.SenApplication;
import sen.wedding.com.weddingsen.utils.SystemUtil;

/**
 * 崩溃日志的管理类,用来捕获,保存,处理崩溃日志
 * Created by lorin on 16/3/9.
 */
public class CrashManager {

    public static final int CRASH_NOT_SENT = 0;
    public static final int CRASH_SENT = 1;

    private CrashManager() {
    }

    private static CrashDataClient dataClient = null;
    private static CrashCloseFunction closeFunction;

    private static Thread.UncaughtExceptionHandler defaultExceptionHandler;

//    public static String crashUploadUrl;

    /**
     * 初始化方法需应用的Application继承QCApplication
     */

    public static void init() {
        CrashManager.init(null);
    }

    public static void init(CrashCloseFunction crashCloseFunction) {
//        crashUploadUrl = url;
        closeFunction = crashCloseFunction;

        if (null == dataClient) {
            dataClient = new CrashDataClient(LogDBHelper.getInstance());
        }

        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                saveCrashData(ex);
                processCrash(thread, ex);
            }
        });

    }

    /**
     * 保存崩溃时信息,包含了应用的启动时间和崩溃时间一起崩溃时的设备相关信息等
     */
    private static void saveCrashData(Throwable ex) {
        StringBuffer sb = new StringBuffer();

        long crashTime = System.currentTimeMillis();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        printWriter.close();
        sb.append(stringWriter.toString());

        CrashInfoModel info = new CrashInfoModel();

        info.setHasSent(0);
        String uuid = DeviceUuidFactory.getUUID(SenApplication.getInstance());
        info.setUuid(uuid);
        info.setDate(DateFormat.format("yyyy-MM-dd kk:mm:ss", crashTime).toString());
        info.setApp(SystemUtil.getPackageName(SenApplication.getInstance()));
        info.setAppv(SystemUtil.getVersionName(SenApplication.getInstance()));
        info.setDev(SystemUtil.getPhoneModel());
        info.setSys("Android");
        info.setSysv(SystemUtil.getAndroidVersion());
        info.setHasSent(CRASH_NOT_SENT);
        String[] strArr = sb.toString().split("\\\n", -1);
        info.setCause(strArr[0]);

        info.setExp(sb.toString());
        info.setStack("");
        info.setExtra("");

        dataClient.addCrashInfo(info);
    }

    /**
     * 处理应用崩溃
     */
    private static void processCrash(Thread thread, Throwable ex) {
        Context context = SenApplication.getInstance();
        if (SystemUtil.isDebuggable(context)) {
            defaultExceptionHandler.uncaughtException(thread, ex);
            return;
        }

        showToast(context.getString(R.string.app_crash));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (null == closeFunction) {
            System.exit(0);
        } else {
            closeFunction.exitAction();
        }
    }

    /**
     * 获取最近一条崩溃CrashInfo对象
     *
     * @return CrashInfo对象
     */
    public static CrashInfoModel getLatestCrashInfo() {
        return dataClient.queryLatestCrashInfo();
    }

    /**
     * 获取所有的崩溃信息对象列表
     *
     * @return List<CrashInfo>
     */
    public static List<CrashInfoModel> getAllCrashInfos() {
        return dataClient.queryAllCrashInfos();
    }

    /**
     * 获取对应发送状态
     *
     * @return List<CrashInfo>
     */
    public static void modeifyInfoSendStatus(List<CrashInfoModel> idList, int status) {
        dataClient.motifyMessageStatus(idList, status);
    }

    /**
     * 获取对应发送状态
     *
     * @return List<CrashInfo>
     */
    public static List<CrashInfoModel> getCrashInfoByStatus(String status) {
        return dataClient.queryInfosBySendingStatus(status);
    }

    /**
     * 清空所有的崩溃信息
     */
    public static void clearAllCrashInfos() {
        dataClient.clearCrashInfos();
    }

    private static void showToast(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(SenApplication.getInstance(), message, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }


}
