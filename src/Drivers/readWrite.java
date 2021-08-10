package Drivers;

import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class readWrite {



    public File[] readFromDirectory(String directory){

        File fileDirectory;

        if(regexFilePath(directory)){
            fileDirectory = new File(directory.stripTrailing());
        }else{
            System.err.println("Illegal argument passed! Check your directories, something got missed in translation");
            throw new IllegalArgumentException();
        }

        File[] list = null;

        if(!fileDirectory.isFile()){
            list = fileDirectory.listFiles();
        }

        return list;
    }

    //Use to write our settings to a simple config file. Recommend we format excessively in program, otherwise we might end up overwriting previous
    //settings.
    public void writeToDirectory(String input){
        try{
            FileWriter writer;
            writer = new FileWriter("config.txt");

            writer.write(input);

            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public boolean regexFilePath(String directory){

        String regex = "^[a-zA-Z]:((\\/|\\\\)(?![\\/\\\\*\"<>?|])[\\w @#$%!~,.\\[\\]()-=+']+(\\/|\\\\)?)*$";

        //The above regex does as follows: Checks for the drive letter, and then colon forward slash. After that, the regex matches any word characters and a few non word characters
        //and then optionally matches a forward slash at the end. The beginning and end position markers allow for the pattern to match the entire string.
        //This will also match any extra words past the optional forward slash.

        Pattern regexPattern = Pattern.compile(regex);

        Matcher matcher = regexPattern.matcher(directory.stripTrailing());

        return matcher.matches();
    }

}
