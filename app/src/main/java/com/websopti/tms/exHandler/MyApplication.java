package com.websopti.tms.exHandler;

import android.app.Application;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Hiren Kapuria on 8/17/2016.
 */
@ReportsCrashes(formKey = "")
public class MyApplication extends MultiDexApplication {

    /**
     * @source http://www.intertech.com/Blog/android-handling-the-unexpected/
     */
    private Thread.UncaughtExceptionHandler androidDefaultUEH;
    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {

        public void uncaughtException(Thread thread, Throwable ex) {

            androidDefaultUEH.uncaughtException(thread, ex);
            //writeFile(ex);
        }
    };

    @Override
    public void onCreate() {

        MultiDex.install(getApplicationContext());
        super.onCreate();
        ACRA.init(this);
        ACRAReportSender reportSender = new ACRAReportSender("alex.test1990@gmail.com", "testadmin");
        ACRA.getErrorReporter().setReportSender(reportSender);

        androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }


    /**
     * Write exception into database
     * @param exception
     */
    private void writeFile(Throwable exception) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        File dir = new File (Environment.getExternalStorageDirectory() + "/TMS/Crash Report");
        if(!dir.exists())
         dir.mkdirs();
        File file = new File(dir, format.format(new Date()).toString()+".txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            exception.printStackTrace(pw);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}