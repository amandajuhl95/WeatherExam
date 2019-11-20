/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author benja
 */
public class DataFacade {
    
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();
     private static DataFacade instance;
     
    
    public static DataFacade getDataFacade() {
        if (instance == null) {

            instance = new DataFacade();
        }
        return instance;
    }
    
        public String getData(String endpoint) throws MalformedURLException, IOException {
        URL url = new URL("https://www.metaweather.com/api/location/"+ endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }
    
        
        
        
        
   
        
        
        
        
        
        
        
        public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        
        DataFacade df = DataFacade.getDataFacade();
        
        String json = df.getData("23424808");
        
            System.out.println(json);
        
    }
}
