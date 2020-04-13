package utils;

import models.City;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSPLoader {

    private String datasetName;
    private String type;
    private String comment;
    private int dimension;
    private String weightType;
    private List<City> cities;

    public String getDatasetName() {
        return datasetName;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public int getDimension() {
        return dimension;
    }

    public String getWeightType() {
        return weightType;
    }

    public List<City> getCities() {
        return cities;
    }

    public TSPLoader(String filename){
        BufferedReader reader;
        List<City> resultCities = new ArrayList<>();
        try {
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            Path filePath = Paths.get(currentPath.toString(), "TSP", filename);
            File file;
            if (filePath.toFile().exists()) {
                file = filePath.toFile();
            } else {
                throw new FileNotFoundException();
            }
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            boolean readyToRead = false;
            while (line != null) {
                if(line.startsWith("NAME")){
                    this.datasetName = line.substring(line.lastIndexOf(":" )+2);
                }
                else if(line.startsWith("TYPE")){
                    this.type = line.substring(line.lastIndexOf(":" )+2);
                }
                else if(line.startsWith("COMMENT")){
                    this.comment = line.substring(line.lastIndexOf(":" )+ 2);
                }
                else if(line.startsWith("DIMENSION")){
                    this.dimension = Integer.parseInt(line.substring(line.lastIndexOf(":") + 2));
                }
                else if(line.startsWith("EDGE_WEIGHT_TYPE")){
                    this.weightType = line.substring(line.lastIndexOf(":") + 2);
                }
                if(line.equals("NODE_COORD_SECTION")){
                    readyToRead = true;
                }
                else{
                    if(line.equals("EOF"))
                        break;
                    if(readyToRead){
                        resultCities.add((parseCity(line)));
                    }
                }
                line = reader.readLine();
            }
            this.cities = resultCities;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static City parseCity(String line){
        line = line.replaceAll("  ", " ");
        String[] splitLine = line.split(" ");
        int cityId = Integer.parseInt(splitLine[0]);
        float x_coordinate = Float.parseFloat(splitLine[1]);
        float y_coordinate = Float.parseFloat(splitLine[2]);
        return new City(cityId, x_coordinate, y_coordinate);
    }

}
