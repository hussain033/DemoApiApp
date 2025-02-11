//package com.example.consoleApp.service;
//
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class SchedulerDemo {
//
//    // time to be shown for every minute // every 3 seconds
//    @Scheduled(cron = "0 * 15 * * ?")
//    public void showTime() throws InterruptedException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy HH:mm:ss.SSS");
//
//        String strDate = dateFormat.format(new Date());
//
//        System.out.println(
//                "Cron job Scheduler: Job running at - "
//                        + strDate);
//        Thread.sleep(2000);
//    }
//
//    // time to be shown every 10 seconds
//    @Scheduled(fixedRate = 10000)
//    @Async
//    public void showTime1() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy HH:mm:ss.SSS");
//
//        String strDate = dateFormat.format(new Date());
//
//        System.out.println(
//                "fixed rate job Scheduler: Job running at - "
//                        + strDate);
//    }
//
//    // starts 15 seconds after the application started
//    // shows time for every 5 seconds
//    @Scheduled(fixedDelay = 5000, initialDelay = 15000)
//    @Async
//    public void showTime2() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy HH:mm:ss.SSS");
//
//        String strDate = dateFormat.format(new Date());
//
//        System.out.println(
//                "fixed delay job Scheduler: Job running at - "
//                        + strDate);
//    }
//
//}
