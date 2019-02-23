package config;

import strings.Strings;

import java.io.*;

public class Config {


    //Var that load's the configFile by default.
    private File configFile =  new File(Strings.PATH_TO_CONFIG_FILE+Strings.NAME_CONFIG_FILE);

    /*
    * Method that creates the config file.
    * */
    public void CreateConfigFile() throws IOException {
        FileWriter fw = new FileWriter(configFile);
        fw.write("name = \"\";\ncsv =\"\";");
        fw.close();
    }

    /*
    * Method that write the content of the config file.
    * */
    public void WriteContent(String hospitalName,String csvPath) throws IOException {
        FileWriter fw = new FileWriter(configFile);
        fw.write("name = \""+hospitalName+"\"; \ncsv=\""+csvPath+"\"");
        fw.close();
    }

    /*
    * Method that reads line by line the content of the config file.
    * */
    public String[] ReadContent() throws IOException {
        String[] content = new String[2];
        FileReader fr = new FileReader(configFile);
        BufferedReader br = new BufferedReader(fr);
        int i=0;
        boolean eof = false;
        String linea = br.readLine();
        while(!eof) {
            content[i] = linea;
            i++;
            linea = br.readLine();
            if(linea == null) {
                eof=true;
            }
        }
        br.close();
        fr.close();

        return content;
    }

    /*
    * Method that loads the value of each line.
    * */
    public String ReadValueOfContent(String content) {
        String[] arrayContent = content.split("\"");
        String value = arrayContent[1];

        return value;
    }
}
