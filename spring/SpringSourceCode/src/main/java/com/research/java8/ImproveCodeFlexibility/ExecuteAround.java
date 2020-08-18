package com.research.java8.ImproveCodeFlexibility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {

    public static void main(String[] args) {
        try {
            String oneLine = processFile(b -> {
                return b.readLine();
            });
            System.out.println(oneLine);

            String twoLine = processFile(b -> b.readLine() + b.readLine());
            System.out.println(twoLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {

        String path= path=ExecuteAround.class.getResource("/").getPath();

        File file=new File(path);
        String filePath=file.getPath()+File.separator+"db.properties";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            return p.process(bufferedReader);
        }
    }

    interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

}
