import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WeatherBot {
    public static void main(String[] args) {
        ConsoleBot bot = new ConsoleBot();
        bot.start();
    }
}

class User {
    private final long id;
    private final String name;
    private final String city;

    public User(long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
}

// Хранилище пользователей
class UserBank {
    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    public User save(String name, String city) {
        User newUser = new User(idCounter++, name, city);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public User findById(long id) {
        return users.get(id);
    }
}

class WeatherService {
    public String getForecast(String city) {
        String normalizedCity = city.toLowerCase().trim();
        return switch (normalizedCity) {
            case "пермь" -> "В Перми сегодня дождь, целый день идет дождь, +5°C.";
            case "найт-сити" -> "Доброе утро, Найт-Сити! Вчерашний подсчёт трупов закончился на крепкой тридцаточке!";
            case "москва" -> "В Москве сегодня облачно, возможен небольшой дождь, +15°C.";
            case "екатеринбург" -> "В Екатеринбурге прохладно, переменная облачность, +10°C.";
            case "челябинск" -> "В Челябинске ясно, без осадков, +12°C.";
            case "санкт-петербург" -> "В Питере как всегда: ветер, дожди, берите зонт, +13°C.";
            default -> "Прогноз для города " + city + " пока не добавлен в мою базу, но там наверняка отличная погода!";
        };
    }
}

class ConsoleBot {
    private final UserBank userBank;
    private final WeatherService weatherService;
    private final Scanner scanner;

    public ConsoleBot() {
        this.userBank = new UserBank();
        this.weatherService = new WeatherService();
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public void start() {
        System.out.println("Привет! Я консольный погодный бот.");
        System.out.println("Давай познакомимся. Как тебя зовут?");
        String name = scanner.nextLine();

        System.out.println("Из какого ты города?");
        String city = scanner.nextLine();


        User currentUser = userBank.save(name, city);

        System.out.println("Отлично, " + currentUser.getName() + "! Твой ID в системе: " + currentUser.getId());
        System.out.println("Введи 'погода', чтобы узнать прогноз, 'профиль', чтобы увидеть свои данные, или 'выход' для завершения.");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().toLowerCase().trim();

            if (command.equals("выход")) {
                System.out.println("До встречи!");
                break;
            } else if (command.equals("погода")) {
                String forecast = weatherService.getForecast(currentUser.getCity());
                System.out.println(forecast);
            } else if (command.equals("профиль")) {
                System.out.println("Твой профиль: Имя - " + currentUser.getName()
                        + ", Город - " + currentUser.getCity()
                        + ", ID - " + currentUser.getId());
            } else {
                System.out.println("Неизвестная команда. Доступные команды: погода, профиль, выход.");
            }
        }
    }
}