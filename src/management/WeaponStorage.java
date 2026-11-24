package management;

import java.io.*;
import java.util.ArrayList;
import model.Weapon;

public class WeaponStorage 
{
    private final File file;
    
    public WeaponStorage(String filePath)
    {
        this.file = new File(filePath);
    }

    // Serialize weapon collection to file
    public void saveWeapons(ArrayList<Weapon> weapons) throws IOException
    {
        File directory = file.getParentFile();

        if (directory != null)
        {
            directory.mkdirs();
        }

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file)))
        {
            output.writeObject(weapons);
        }
    }

    // Deserialize weapon collection from file
    @SuppressWarnings("unchecked")
    public ArrayList<Weapon> loadWeapons() throws IOException, ClassNotFoundException
    {
        if (!file.exists())
        {
            return new ArrayList<>();
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)))
        {
            return (ArrayList<Weapon>) input.readObject();
        }
    }
}
