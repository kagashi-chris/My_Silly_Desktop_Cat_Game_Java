package com.zhen.MySillyDesktopCatGame.Util;

import com.zhen.MySillyDesktopCatGame.Model.Cat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FileUtil {

    public static String generateCatString(Cat cat)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(cat.getCatLastUpdated());
        sb.append(cat.getHappiness());
        sb.append(cat.getFullness());
        return sb.toString();
    }

    public static void csvWriter(Cat cat)
    {
        Path p3 = Paths.get(URI.create("file:/C:/Users/CZ/Code/MySillyDesktopCatGane2/src/main/resources/Cat.csv"));
        Charset charset = Charset.forName("US-ASCII");
        String s = generateCatString(cat);
        try (BufferedWriter writer = Files.newBufferedWriter(p3, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public static Cat csvReader(String path)
    {
        Cat cat = new Cat(150,150);
        String line = "";
        String[] catInfo;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(FileUtil.class.getClassLoader().getResource(path).openStream()));

            if((line = br.readLine()) != null)
            {
                catInfo = line.split(",");

                //catInfo[0] is date time
                //catInfo[1] is happiness
                //catInfo[2] is hunger
                cat.setCatLastUpdated(LocalDateTime.parse(catInfo[0]));
                cat.setHappiness(Integer.valueOf(catInfo[1]));
                cat.setFullness(Integer.valueOf(catInfo[2]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cat;
    }
}