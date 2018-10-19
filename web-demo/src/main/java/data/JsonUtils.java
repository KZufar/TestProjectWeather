package data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonUtils {

    /**
     * Метод для получения данных по указанной ссылке
     *
     * @param url - ссылка в виде объекта URL (Uniform Resource Locator)
     * @return содержимое страницы на указанной ссылке в @param url
     */
    public static String parseUrl(URL url) {
        if (url == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        // открываем соедиение к указанному URL
        // помощью конструкции try-with-resources
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            // построчно считываем результат в объект StringBuilder
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    // парсим некоторые данные о погоде
    public static void parseCurrentWeatherJson(String resultJson) {
        // конвертируем строку с Json в JSONObject для дальнейшего его парсинга
        JsonObject weatherJsonObject = (JsonObject) new JsonParser().parse(resultJson);
        //JsonObject weatherJsonObject1 = (JsonObject) weatherJsonObject.get("list");
        //JsonObject weatherJsonObject2 = (JsonObject) weatherJsonObject1.get("0");

        System.out.println(resultJson);
        // получаем название города, для которого смотрим погоду
        System.out.println("Название города: " + weatherJsonObject.get("name") + " "+weatherJsonObject.get("main"));

        // получаем массив элементов для поля weather
            /* ... "weather": [
            {
                "id": 500,
                    "main": "Rain",
                    "description": "light rain",
                    "icon": "10d"
            }
            ], ... */
        JsonArray array = new JsonArray();
        JsonArray array1 = new JsonArray();
        JsonArray weatherArray = (JsonArray) weatherJsonObject.get("list");
        JsonObject[] j = new JsonObject[40];
        JsonObject temp = new JsonObject();
        JsonObject tempWeather = new JsonObject();
        int count = 16;
        int i = 8;
        int l = 1;
        for(int k = i;k<count;k++) {
            JsonObject weatherData = (JsonObject) weatherArray.get(k);
            JsonArray array2 = (JsonArray) weatherData.get("weather");
            array.add(weatherData.get("main"));
            temp = (JsonObject) array.get(l-1);
            tempWeather = (JsonObject) array2.get(0);
//            request.setAttribute("temp"+String.valueOf(l), temp.get("temp"));
//            request.setAttribute("time"+String.valueOf(l), weatherData.get("dt_txt"));
//            request.setAttribute("weather"+String.valueOf(l), tempWeather.get("description"));
            l++;

            System.out.println("Время "+weatherData.get("dt_txt")
                    +"\n Температура " + temp.get("temp")
                    +"\n Погода " + tempWeather.get("description"));
        }

        //array.add(weatherJsonObject1.get());

        //JsonObject temp = (JsonObject) array.get(0);
//        array.add(());
        // печатаем текущую погоду в консоль
//        System.out.println("Погода на данный момент: " + weatherData.get("main")+" " + temp.get("temp"));
        // и описание к ней
//        System.out.println("Более детальное описание погоды: " + weatherData.get("description"));

    }

    // формируем новый JSON объект из нужных нам погодных данных
    public static String buildWeatherJson() {
        // для простоты примера просто хардкодим нужные данные в методе
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Лондон");
        jsonObject.put("main", "Солнечно");
        jsonObject.put("description", "Мороз трескучий, На небе ни единой тучи");

        return jsonObject.toJSONString();
    }

    // создаем объект URL из указанной в параметре строки
    public static URL createUrl(String link) {
        try {
            return new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}