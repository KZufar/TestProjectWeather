package com.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data.JsonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("Hello, World!");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        String city = request.getParameter("city");


        Calendar c = new GregorianCalendar();              // календарь на текущую дату
//        Calendar c2 = new GregorianCalendar(2014, 12, 21); // календарь на 21.12.2014
        c.add(Calendar.DAY_OF_YEAR, 1);

        String date = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        String unit = request.getParameter("unit");
        String wantDate = request.getParameter("date");
        int i = 0, count = 40;
        if (wantDate.equals("d1")){
            i = 0;
            count = 8;
        }else if (wantDate.equals("d2")){
            i = 8;
            count = 16;
        }else if (wantDate.equals("d3")){
            i = 16;
            count = 24;
        }else if (wantDate.equals("d4")){
            i =24;
            count = 32;
        }else if (wantDate.equals("d5")){
            i = 32;
            count = 40;
        }
        String units = "metric";
        if (unit.equals("s2")){
            units = "imperial";
        }
        System.out.println(unit);
        System.out.println(units);
        String WEATHER_URL =
                "http://api.openweathermap.org/data/2.5/forecast?q=" + city +
                        ",ru&lang=ru&units="+units+"&appid=214397cdd772dae4a6c5ac911906f9ab";

        URL url = JsonUtils.createUrl(WEATHER_URL);

        // загружаем Json в виде Java строки
        String resultJson = JsonUtils.parseUrl(url);
        System.out.println("Полученный JSON:\n" + resultJson);


        JsonObject weatherJsonObject = (JsonObject) new JsonParser().parse(resultJson);

        if (city==null) city = "Kazan";
        request.setAttribute("city", city);

        JsonArray array = new JsonArray();
        JsonArray array1 = new JsonArray();
        JsonArray weatherArray = (JsonArray) weatherJsonObject.get("list");
        JsonObject[] j = new JsonObject[40];
        JsonObject temp, tempWeather;
        int l = 1;
        for(int k = i;k<count;k++) {
            JsonObject weatherData = (JsonObject) weatherArray.get(k);
            JsonArray array2 = (JsonArray) weatherData.get("weather");
            array.add(weatherData.get("main"));
            temp = (JsonObject) array.get(l-1);
            tempWeather = (JsonObject) array2.get(0);
            request.setAttribute("temp"+String.valueOf(l), temp.get("temp"));
            request.setAttribute("time"+String.valueOf(l), weatherData.get("dt_txt"));
            System.out.println("icon"+String.valueOf(l) + "   "+tempWeather.get("icon"));
            request.setAttribute("weather"+String.valueOf(l), tempWeather.get("description"));
            request.setAttribute("icon"+String.valueOf(l), tempWeather.get("icon"));
            l++;
        }
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("response.jsp").forward(request,response);

//        System.out.println("eagserbser" + c.getTime());
//        array.add(weatherJsonObject.get("main"));
//        array.add(weatherJsonObject.get("clouds"));
//        JSONObject mainData = (JSONObject) array.get(0);
//        JSONObject cloudsData = (JSONObject) array.get(1);
//        System.out.println(mainData.get("temp"));


//        request.setAttribute("temp", mainData.get("temp"));
//        request.setAttribute("weather", weatherData.get("description"));
//        request.setAttribute("all", cloudsData.get("all"));
//        request.setAttribute("date1", date);
//        c.add(Calendar.DAY_OF_YEAR, 1);
//        request.setAttribute("date2", c.getTime());
//        c.add(Calendar.DAY_OF_YEAR, 1);
//        request.setAttribute("date3", c.getTime());
//        c.add(Calendar.DAY_OF_YEAR, 1);
//        request.setAttribute("date4", c.getTime());
//        c.add(Calendar.DAY_OF_YEAR, 1);
//        request.setAttribute("date5", c.getTime());
    }
}
