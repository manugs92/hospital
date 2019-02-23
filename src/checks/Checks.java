package checks;

import strings.Strings;
import java.io.*;


public class Checks {

    /*
    * Method that checks if the config file exist. (config.txt)
    * This file, contains the last name and the last csv loaded in the program.
    * This is very usefull to don't spend time loading the same file each time you run the program again.
    * */
    public File ifConfigFileExist() throws IOException {
        File configFile = new File(Strings.PATH_TO_CONFIG_FILE+Strings.NAME_CONFIG_FILE);
        FileReader fr = new FileReader(configFile);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        br.close();
        fr.close();

        return  configFile;
    }

    /*
    * Method that checks the extension of the file loaded.
    * If it's a CSV, it returns true, else false.
    * */
    public boolean extensionCSV(File path)  {
        String[] splitedPath = path.toString().split("\\\\");
        String fileName = splitedPath[splitedPath.length-1];
        String extension = fileName.replaceAll(".+[(.)]","").toLowerCase();
        if(extension.equals("csv")) {
            return true;
        }else {
            return false;
        }

    }
}
