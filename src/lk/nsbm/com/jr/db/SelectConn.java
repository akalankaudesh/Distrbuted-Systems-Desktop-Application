package lk.nsbm.com.jr.db;

public class SelectConn {
    public static boolean con1 = true;
    public static boolean con2 = false;
    public static boolean con3 = false;

    public static void changeConn(String selection) {
         if (selection.contains("2")) {
             con1=false;
             con2=true;
             con3=false;

        } else if (selection.contains("3")) {
             con1=false;
             con2=false;
             con3=true;

        }


    }

}
