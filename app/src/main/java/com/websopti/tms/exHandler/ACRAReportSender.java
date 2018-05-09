package com.websopti.tms.exHandler;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import android.util.Log;

/**
 * Created by Hiren Kapuria on 8/17/2016.
 */
public class ACRAReportSender implements ReportSender {

    private String emailUsername ;
    private String emailPassword ;

    public ACRAReportSender(String emailUsername, String emailPassword) {
        super();
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
    }


    @Override
    public void send(CrashReportData report) throws ReportSenderException {

        String reportBody = createCrashReport(report);
        GMailSender gMailSender = new GMailSender(emailUsername, emailPassword);
        try {
            gMailSender.sendMail("TMS-Crash Report", reportBody, emailUsername, "rahulp@websoptimization.com,hirenk@websoptimization.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** Extract the required data out of the crash report.*/
    private String createCrashReport(CrashReportData report) {

        // I've extracted only basic information.
        // U can add loads more data using the enum ReportField. See below.
        StringBuilder body = new StringBuilder();
        body
                .append("Device : " + report.getProperty(ReportField.BRAND) + "-" + report.getProperty(ReportField.PHONE_MODEL))
                .append("\n")
                .append("Android Version :" + report.getProperty(ReportField.ANDROID_VERSION))
                .append("\n")
                .append("App Version : " + report.getProperty(ReportField.APP_VERSION_CODE))
                .append("\n")
                .append("STACK TRACE : \n" + report.getProperty(ReportField.STACK_TRACE));

        return body.toString();
    }
}
