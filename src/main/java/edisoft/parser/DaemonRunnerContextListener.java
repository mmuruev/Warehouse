package edisoft.parser;


import javax.servlet.ServletContextEvent;

public class DaemonRunnerContextListener implements javax.servlet.ServletContextListener{
    private WatcherThread watcherThread = null;

     public void contextInitialized(ServletContextEvent sce) {
         if ((watcherThread == null) || (!watcherThread.isAlive())) {
             watcherThread = new WatcherThread();
             watcherThread.setDaemon(true);
             watcherThread.start();
         }
     }

     public void contextDestroyed(ServletContextEvent sce){
         try {
           //  watcherThread.doShutdown();
             watcherThread.interrupt();
         } catch (Exception ex) {
         }
     }
}
