package nl.frankkie.desktopandroidtools;

/**
 *
 * @author fbouwens
 */
public class GcmViaAdb {

    //http://stackoverflow.com/questions/25931902/is-it-possible-to-simulate-a-gcm-receive-from-the-adb-shell-am-command-line-i
    
    //http://stackoverflow.com/questions/27940118/sending-json-as-extra-data-in-an-android-broadcast-via-adb-gets-incorrectly-form/29428061#29428061
    
    public static final String template = "adb shell am broadcast -c %s -a com.google.android.c2dm.intent.RECEIVE -e data %s";
    
    public static void main(String[] args) {
        String packageName = args[0];
        String json = args[1];
        
        System.out.println(String.format(template, packageName, json));
    }
    
}
