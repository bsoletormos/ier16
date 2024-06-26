package src.env;

import jason.asSyntax.*;
import jason.environment.*;
import src.env.model.SmartHomeGui;
import src.env.model.SmartHomeModel;
import src.env.model.Weather;

import java.util.logging.Logger;

public class SmartHomeEnvironment extends Environment {
    private SmartHomeModel model;
    private final Logger logger = Logger.getLogger(SmartHomeEnvironment.class.getName());

    public static final Literal TOGGLE_LIGHT = Literal.parseLiteral("toggleLight(light)");
    public static final Literal SET_TEMPERATURE = Literal.parseLiteral("setTemperature(temperature)");
    public static final Literal OPEN_WINDOW = Literal.parseLiteral("openWindow(window)");
    public static final Literal CLOSE_WINDOW = Literal.parseLiteral("closeWindow(window)");
    public static final Literal OPEN_DOOR = Literal.parseLiteral("openDoor(door)");
    public static final Literal CLOSE_DOOR = Literal.parseLiteral("closeDoor(door)");

    public static final Literal SET_WEATHER = Literal.parseLiteral("setWeather(weather)");

    @Override
    public void init(String[] args) {
        super.init(args);
        model = new SmartHomeModel();
        model.addObserver((o, arg) -> updatePercepts());
        SmartHomeGui smartHomeGui = new SmartHomeGui(this);
        logger.info("SmartHomeEnvironment initialized with args.");
    }
public SmartHomeModel getModel() {return model;}
    @Override
    public boolean executeAction(String agName, Structure action) {
        String act = action.getFunctor();
        try {
            switch (act) {
                case "toggleLight":
                    model.toggleDeviceState("light");
                    break;
                case "setTemperature":
                    model.setTemperature(Double.parseDouble(action.getTerm(0).toString()));
                    break;
                case "openWindow":
                    model.setDeviceState("window", true);
                    break;
                case "closeWindow":
                    model.setDeviceState("window", false);
                    break;
                case "openDoor":
                    model.setDeviceState("door", true);
                    break;
                case "closeDoor":
                    model.setDeviceState("door", false);
                    break;
                case "setWeather":
                    String weatherStr = action.getTerm(0).toString();
                    Weather weather = Weather.valueOf(weatherStr.toUpperCase());
                    //setWeather(weather);
                    break;
                default:
                    logger.severe("Action " + act + " not supported");
                    return false;
            }
            logger.info("Executed action " + act + " by agent " + agName);
            return true;
        } catch (Exception e) {
            logger.severe("Error executing action: " + e.getMessage());
            return false;
        }
    }
    public void setRainyWeather(boolean isRainy) {
        //model.setRainy(isRainy);
        updatePercepts(); // Frissítjük a perceptusokat az időjárás változása után
    }
    public void updatePercepts() {
        clearAllPercepts();
        addPercept(Literal.parseLiteral("light(" + model.getDeviceState("light") + ")"));
        addPercept(Literal.parseLiteral("temperature(" + model.getTemperature() + ")"));
        addPercept(Literal.parseLiteral("window(" + model.getDeviceState("window") + ")"));
        addPercept(Literal.parseLiteral("door(" + model.getDeviceState("door") + ")"));
        addPercept(Literal.parseLiteral("weather(" + model.getCurrentWeather().name().toLowerCase() + ")"));
        addPercept(Literal.parseLiteral("time(" + model.getTime() + ")"));

        logger.fine("Updated percepts based on the environment state.");
    }
}