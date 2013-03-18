package group.id.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FileReaderIterable implements Iterable<String> {

    private static Logger log= LoggerFactory.getLogger(FileReaderIterable.class);

    private String filePath;

    public FileReaderIterable(String filePath) {
        this.filePath=filePath;
    }

    public FileReaderIterable() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Iterator<String> iterator() {
        List<String> strings = new LinkedList<String>();
        if(filePath==null){
            return null;
        }
        BufferedReader reader;
        try {
            reader=new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            log.warn("Exception during read file: {}",filePath);
            return  null;
        }
        try {
            String str;
            while((str=reader.readLine())!=null){
                strings.add(str);
            }
        }
        catch (IOException e) {
            log.warn("Exception during read file: {}",filePath);
        }

        return strings.iterator();
    }
}