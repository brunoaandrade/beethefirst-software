package replicatorg.drivers.commands;

import replicatorg.drivers.Driver;
import replicatorg.drivers.RetryException;

public class ReadZValue implements DriverCommand {

    @Override
    public void run(Driver driver) throws RetryException {
        driver.readZValue();
    }

    @Override
    public String getCommand() {
        return "M600";
    }

    @Override
    public void setCommand(String newCommand) {
    }

    @Override
    public boolean isPrintingCommand() {
        return false;
    }
}
