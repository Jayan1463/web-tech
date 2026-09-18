package com.grocery.web;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

@WebListener
public class JdbcCleanupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        AbandonedConnectionCleanupThread.checkedShutdown();

        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            if (driver.getClass().getClassLoader()
                    == getClass().getClassLoader()) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ignored) {
                }
            }
        }
    }
}
