package pt.beeverycreative.beesoft.filaments;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import pt.beeverycreative.beesoft.drivers.usb.PrinterInfo;
import replicatorg.app.Base;

/**
 * Copyright (c) 2013 BEEVC - Electronic Systems This file is part of BEESOFT
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version. BEESOFT is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with
 * BEESOFT. If not, see <http://www.gnu.org/licenses/>.
 */
public class FilamentControler {

    public static final String NO_FILAMENT = "none";
    public static final String NO_FILAMENT_2 = "no_file";
    public static final String NO_FILAMENT_CODE = "A000";
    public static final int DEFAULT_NOZZLE_SIZE = 400;

    private static final Map<String, Filament> filamentMap = new TreeMap<>();
    private static final Set<Nozzle> nozzleSet = new TreeSet<>();
    private static PrinterInfo currentPrinterFilamentList = null;
    private static final String filamentsDir = Base.getAppDataDirectory() + "/filaments/";

    /**
     * Initializes list of filaments for a given printer. Does nothing if the
     * current list contains the filaments for the given printer
     *
     * @param printer Printer for which the list of filaments will be
     * initialized
     */
    public static void initFilamentList(PrinterInfo printer) {
        Base.writeLog("Initial filament fetch for printer " + printer, FilamentControler.class);
        Base.writeLog("Printer code for filament purposes: " + printer.filamentCode(), FilamentControler.class);

        if (currentPrinterFilamentList == null
                || !currentPrinterFilamentList.filamentCode().equals(printer.filamentCode())) {
            fetchFilaments(printer);
            currentPrinterFilamentList = printer;
        } else {
            Base.writeLog("No fetch is necessary, list already contains the "
                    + "filaments for this printer", FilamentControler.class);
        }
    }

    /**
     * Gets the current list of Filament objects present in the filaments
     * directory parsing the available xml files
     *
     * @param connectedPrinter PrinterInfo of connected printer. Pass null to
     * fetch it from driver
     */
    public static void fetchFilaments(PrinterInfo connectedPrinter) {

        PrinterInfo printer;

        if (connectedPrinter != null) {
            printer = connectedPrinter;
        } else {
            printer = Base.getMainWindow().getMachineInterface().getDriver().getConnectedDevice();
        }

        // get all the files from a directory
        File directory = new File(filamentsDir);
        File[] fList = directory.listFiles();

        if (fList != null) {
            List<File> filamentFiles = new ArrayList<>();

            for (File file : fList) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    filamentFiles.add(file);
                }
            }

            filamentMap.clear();
            nozzleSet.clear();

            JAXBContext jc;
            Unmarshaller unmarshaller;
            try {
                jc = JAXBContext.newInstance(Filament.class);
                unmarshaller = jc.createUnmarshaller();

                //Parses all the files
                filamentFiles.stream().forEach((ff) -> {
                    Filament fil;
                    try {
                        fil = (Filament) unmarshaller.unmarshal(ff);

                        // only add to available filaments if it is supported by
                        // the printer, or if no printer is connected
                        fil.getSupportedPrinters().stream().filter((sc) -> (printer.filamentCode().equals(sc.getPrinterName())
                                || printer.filamentCode().equals("UNKNOWN"))).forEach((sc) -> {
                                    filamentMap.putIfAbsent(fil.getName(), fil);
                                    sc.getNozzles().stream().forEach(nozzleSet::add);
                                });

                    } catch (JAXBException ex) {
                        Logger.getLogger(FilamentControler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } catch (JAXBException ex) {
                Logger.getLogger(FilamentControler.class.getName()).log(Level.SEVERE, null, ex);
            }

            Base.writeLog("Acquired " + filamentMap.size() + " filaments", FilamentControler.class);
        }
    }

    /**
     * Return an array of nozzles found in the filament files.
     *
     * @return array of nozzle objects
     */
    public static Nozzle[] getNozzleArray() {
        if (nozzleSet != null) {
            return nozzleSet.toArray(new Nozzle[nozzleSet.size()]);
        } else {
            return new Nozzle[0];
        }
    }

    /**
     * Return the filaments that are compatible with a given nozzle
     * configuration, on the currently connected printer.
     *
     * @param nozzle the Nozzle object for which to obtain compatible filaments
     * @return array of compatible filament objects
     */
    public static Filament[] getCompatibleFilaments(Nozzle nozzle) {
        PrinterInfo connectedPrinter;
        List<Filament> filaments;

        connectedPrinter = Base.getMainWindow().getMachineInterface()
                .getDriver().getConnectedDevice();

        if (filamentMap == null) {
            fetchFilaments(connectedPrinter);
        }

        if (filamentMap.isEmpty() == false) {
            filaments = new ArrayList<>();
            for (Map.Entry<String, Filament> fil : filamentMap.entrySet()) {
                for (SlicerConfig sc : fil.getValue().getSupportedPrinters()) {
                    if (sc.getPrinterName().equalsIgnoreCase(connectedPrinter.filamentCode())) {
                        if (sc.getNozzles().stream().anyMatch((noz) -> (noz.equals((nozzle))))) {
                            filaments.add(fil.getValue());
                        }
                        break;
                    }
                }
            }

            return filaments.toArray(new Filament[filaments.size()]);
        } else {
            return new Filament[0];
        }
    }

    public static boolean isFilamentCompatible(Filament filament, Nozzle nozzle) {
        SlicerConfig printer;
        PrinterInfo connectedPrinter;
        
        connectedPrinter = Base.getMainWindow().getMachineInterface().getDriver().getConnectedDevice();
        
        
        // from the list of printers in the filament profile, obtain the part corresponding to the currently connected printer
        printer = filament.getSupportedPrinters().stream().filter((sc) -> connectedPrinter.filamentCode().equals(sc.getPrinterName())).findFirst().get();

        // check if the profile support the nozzle, for the current printer
        return printer.getNozzles().stream().anyMatch((nozObj) -> nozObj.equals(nozzle));
    }

    public static void forceFetch(PrinterInfo printer) {
        fetchFilaments(printer);
    }

    /**
     * Returns an array containing the list of all filaments available for this
     * printer
     *
     * @return array of Filament objects
     */
    public static Filament[] getFilamentArray() {
        if (!filamentMap.isEmpty()) {
            return filamentMap.values().toArray(new Filament[filamentMap.size()]);
        } else {
            return new Filament[0];
        }
    }

    public static Map<String, String> getFilamentDefaults(String coilCode) {
        String logStrHeader;
        Filament fil;

        logStrHeader = "getFilamentDefaults(coilCode=" + coilCode + ") ";

        if (filamentMap == null) {
            fetchFilaments(null);
        }

        if (!filamentMap.isEmpty()) {
            fil = filamentMap.get(coilCode);
            if (fil != null) {
                Base.writeLog(logStrHeader + " returned a list of slicer parameters", FilamentControler.class);
                return fil.getDefaultParametersMap();
            }
        }

        Base.writeLog(logStrHeader + " returned an empty list", FilamentControler.class);
        return null;
    }

    /**
     * Get the Hash map with the filament settings for a specific nozzle size,
     * resolution and printer
     *
     * @param coilCode coil code.
     * @param resolution resolution
     * @param nozzleSize nozzle size, in microns
     * @param printer printer identification
     *
     * @return ratio for each color.
     */
    public static Map<String, String> getFilamentSettings(String coilCode,
            String resolution, int nozzleSize, PrinterInfo printer) {

        String logStrHeader;
        Filament fil;

        logStrHeader = "getFilamentSettings(coilCode=" + coilCode + ", resolution=" + resolution + ", nozzleSize=" + nozzleSize + ", printerId=" + printer.filamentCode() + ") ";

        if (filamentMap == null) {
            fetchFilaments(printer);
        }

        if (!filamentMap.isEmpty()) {
            fil = filamentMap.get(coilCode);
            for (SlicerConfig sc : fil.getSupportedPrinters()) {
                if (printer.filamentCode().equals(sc.getPrinterName())) {
                    for (Nozzle nozzle : sc.getNozzles()) {
                        if (nozzle.getSizeInMicrons() == nozzleSize) {
                            for (Resolution res : nozzle.getResolutions()) {
                                if (res.getType().equalsIgnoreCase(resolution)) {
                                    Base.writeLog(logStrHeader + " returned a list of slicer parameters", FilamentControler.class);
                                    return res.getParametersMap();
                                }
                            }
                        }
                    }
                }

            }
        }

        // Defaults to an empty map
        Base.writeLog(logStrHeader + " returned an empty list", FilamentControler.class);
        return null;
    }

    public static boolean colorExistsLocally(String name) {
        if (filamentMap == null) {
            fetchFilaments(null);
        }

        return filamentMap.get(name) != null;
    }

    public static Filament getMatchingFilament(String filamentText) {
        if (filamentMap == null) {
            fetchFilaments(null);
        }

        return filamentMap.get(filamentText);
    }

}
