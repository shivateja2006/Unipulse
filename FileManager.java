import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private final String fileName = "data/opportunities.txt";

    // Save one opportunity at the end of the file.
    public void saveOpportunity(Opportunity op) {
        try {
            File folder = new File("data");
            if (!folder.exists()) folder.mkdirs();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(clean(op.getTitle()) + "|" + clean(op.getType()) + "|" +
                    clean(op.getOrganizer()) + "|" + clean(op.getDeadline()) + "|" +
                    clean(op.getLink()) + "|" + clean(op.getPostedBy()) + "|" +
                    clean(op.getDescription()));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save opportunity: " + e.getMessage());
        }
    }

    // Read all saved opportunities from the file.
    public ArrayList<Opportunity> loadOpportunities() {
        ArrayList<Opportunity> list = new ArrayList<>();

        try {
            File file = new File(fileName);
            if (!file.exists()) return list;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 7) {
                    list.add(new Opportunity(data[0], data[1], data[2],
                            data[3], data[4], data[5], data[6]));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not read opportunities: " + e.getMessage());
        }

        return list;
    }

    // Prevent the separator from breaking the file format.
    private String clean(String value) {
        if (value == null) return "";
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}
