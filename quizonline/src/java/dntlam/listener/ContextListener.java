/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dntlam.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author sasuk
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String fileName = sc.getInitParameter("indexPageLocation");
        String realPath = sc.getRealPath("/" + fileName);
        File f = new File(realPath);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            if (f.exists()) {
                Map<String, String> indexPage = new HashMap<>();
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split("=");
                    indexPage.put(arr[0], arr[1]);

                }
                sc.setAttribute("INDEXPAGE", indexPage);
                String resourcePath = sc.getContextPath() + "/resource";
                String apiPath = sc.getContextPath() + "/api";
                sc.setAttribute("PATHADMIN", resourcePath + "/admin");
                sc.setAttribute("PATHHOME", resourcePath + "/home");
                sc.setAttribute("PATHAPI", apiPath);
                sc.setAttribute("ADMINURI", sc.getContextPath() + "/admin");
                sc.setAttribute("HOMEURI", sc.getContextPath());
                sc.setAttribute("PATHRESOURCE", resourcePath);
                br.close();
                fr.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
