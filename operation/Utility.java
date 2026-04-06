package operation;

public class Utility {

    /*-------------------------------------------------*/
    // checks and error reporting
    /*-------------------------------------------------*/
    public static void error(String err) {
        System.out.println(err);
        System.exit(-1);
    }

    public static boolean checkFormat8Bit(String data) {
        if (data.length() != 3 || data.charAt(2) != 'h') {
            return false;
        }

        for (int i=0; i<2; i++) {
            if ( (data.charAt(i) < '0' || data.charAt(i) > '9') && (data.charAt(i) < 'A' || data.charAt(i) > 'F') ) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkFormat16Bit(String data) {
        if (data.length() != 5 || data.charAt(4) != 'h') {
            return false;
        }

        for (int i=0; i<4; i++) {
            if ( (data.charAt(i) < '0' || data.charAt(i) > '9') && (data.charAt(i) < 'A' || data.charAt(i) > 'F') ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isData(String data) {
        if (data.charAt(data.length()-1) == 'h' &&
            (Utility.checkFormat16Bit(data) || Utility.checkFormat8Bit(data)))
        {
                return true;
        }
        return false;
    }

    public static boolean isAddress(String data) {
        if (data.length() != 4) return false;

        for (int i=0; i<data.length(); i++) {
            if ((data.charAt(i) < '0' || data.charAt(i) > '9') && (data.charAt(i) < 'A' || data.charAt(i) > 'F')) {
                return false;
            }
        }
        return true;
    }
}