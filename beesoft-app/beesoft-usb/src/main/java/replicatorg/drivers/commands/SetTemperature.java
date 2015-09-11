package replicatorg.drivers.commands;

import replicatorg.drivers.Driver;
import replicatorg.drivers.RetryException;

public class SetTemperature implements DriverCommand {

    double temperature;

    public SetTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public void run(Driver driver) throws RetryException {
        driver.setTemperature(temperature);
    }

    @Override
    public String getCommand() {
        return "M104 S" + temperature;
    }

    @Override
    public void setCommand(String newCommand) {
    }

    @Override
    public boolean isPrintingCommand() {
        return false;
    }
}
