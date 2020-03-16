import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TSPLoader {
    static List<City> loadCities(String name){
        BufferedReader reader;
        List<City> resultCities = new ArrayList<City>();
        try {
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            Path filePath = Paths.get(currentPath.toString(), "TSP", name);
            File file = null;
            if (filePath.toFile().exists()) {
                file = filePath.toFile();
            } else {
                throw new FileNotFoundException();
            }
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            boolean readyToRead = false;
            while (line != null) {
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
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultCities;
    }

    private static City parseCity(String line){
        String[] splitLine = line.split(" ");
        int cityId = Integer.parseInt(splitLine[0]);
        float x_coordinate = Float.parseFloat(splitLine[1]);
        float y_coordinate = Float.parseFloat(splitLine[2]);
        return new City(cityId, x_coordinate, y_coordinate);
    }

}
