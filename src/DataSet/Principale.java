package DataSet;

import java.nio.file.Paths;
import java.util.Scanner;

public class Principale {
    public static void main(String[] args) {
        String dbName = "RobotTraderDataSet";
        MetierDB taskDB = new MetierDB();
        //Peut etre un dossier (qui contient exclusivement les fichier à charge) ou un fichier
        String fileName="C:\\Users\\Hakan\\Desktop\\Dossier dev\\Java\\Projet Robot Trader\\Intraday_srd_05122019\\2019";
        //Le temps d'execution pour un dossier est très long
        //Pour génerer la BD (structure)
        GenerateDB generateur = new GenerateDB();
//        if(generateur.genererDB(taskDB, dbName))
//            System.out.println("\tBD crée avec succée");
//        else
//            System.out.println("\tErreur lors de la création");

        taskDB = new MetierDB(dbName);

        //Pour charger un data set depuis un fichier
        UpLoadDataSet uploader = new UpLoadDataSet(taskDB);

        System.out.println(uploader.chargerDataSet(fileName, ';'));



    }
}
