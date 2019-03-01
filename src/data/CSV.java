package data;



import au.com.bytecode.opencsv.CSVReader;
import strings.Strings;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSV {

    private static String pathCSVFile;


    public void setPathCSVFile(String path) {
        this.pathCSVFile=path;
    }

    /*
    * Method that deletes the row from given CSV.
    * We'had problems to make in the same file, so we create a temp file
    * to copy all the data that it's not the same, and refuse the data
    * that's the same to delete.
    * */
    public static void delete(String data) throws IOException {
        String[] line;
        String fullLine;

        File CSVFile = new File(pathCSVFile);
        File temp = new File(Strings.CSV_PATH+"\\temp.csv");
        FileReader fr = new FileReader(CSVFile);
        CSVReader CSVR = new CSVReader(fr);
        FileWriter fw = new FileWriter(temp);
        BufferedWriter bw = new BufferedWriter(fw);
        boolean eof = false;
        String europeanDateFormat = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(europeanDateFormat);
        line = CSVR.readNext();
        bw.write(line[0]+","+line[1]+","+line[2]+","+line[3]+","+line[4]+","+line[5]+","+line[6]+","+line[7]);
        bw.newLine();
        while(!eof) {
            line = CSVR.readNext();
            if(line != null) {
                fullLine = line[0]+","+line[1]+","+line[2]+","+LocalDate.parse(line[3],formatter)+","+line[4]+","+line[5]+","+Float.valueOf(line[6])+","+line[7];
                if(!fullLine.equals(data)) {
                   bw.write(line[0]+","+line[1]+","+line[2]+","+line[3]+","+line[4]+","+line[5]+","+Float.valueOf(line[6])+","+line[7]);
                   bw.newLine();
                }
            }else {
                eof =true;
            }
        }
        CSVR.close();
        fr.close();
        bw.close();
        fw.close();
        String folderName = CSVFile.getName();
        CSVFile.delete();
        temp.renameTo(new File(Strings.CSV_PATH+"\\"+folderName));
    }
}
