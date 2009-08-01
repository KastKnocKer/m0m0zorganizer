package prove;

import scansioni.padre;
import database.DatabaseMySql;
import database.Orario;
import database.createRootDB;
import download.popularReader;


public class provevideo {

    public static void main(String[] args)
    {
        String str1 = "2010-09-15T01:01:53";
        String str2 = "2009-19-05T02:02:52";

        // Compare the two strings.
        int result = str1.compareTo(str2);

        // Display the results of the comparison.
        if (result < 0)
        {
            System.out.println("\"" + str1 + "\"" +
                " is less than " +
                "\"" + str2 + "\"");
        }
        else if (result == 0)
        {
            System.out.println("\"" + str1 + "\"" +
                " is equal to " +
                "\"" + str2 + "\"");
        }
        else // if (result > 0)
        {
            System.out.println("\"" + str1 + "\"" +
                " is greater than " +
                "\"" + str2 + "\"");
        }
    }
}
