package data;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URL;

public class Main {
    /**
     * Для простоты и удобства используем уже сформированную строку
     * с запросом погоды в Лондоне на данный момент
     * <p>
     * другие примеры запросов можете глянуть здесь
     * {@see <a href="http://openweathermap.org/current">openweathermap</a>}
     * также Вам понадобится свой API ключ
     */
    public static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/forecast?q=Kazan,ru&appid=214397cdd772dae4a6c5ac911906f9ab";
//            "http://api.openweathermap.org/data/2.5/weather?q=Kazan,ru&units=kelvin&appid=241de9349721df959d8800c12ca4f1f3";
//                    "&units=metric&appid=241de9349721df959d8800c12ca4f1f3";

    public static void main(String[] args) throws ParseException {

        // создаем URL из строки
        URL url = JsonUtils.createUrl(WEATHER_URL);

        // загружаем Json в виде Java строки
        String resultJson = JsonUtils.parseUrl(url);
        System.out.println("Полученный JSON:\n" + resultJson);

        // парсим полученный JSON и печатаем его на экран
        JsonUtils.parseCurrentWeatherJson(resultJson);

        // формируем новый JSON объект из нужных нам погодных данных
        String json = JsonUtils.buildWeatherJson();
        System.out.println("Созданный нами JSON:\n" + json);

    }

}
