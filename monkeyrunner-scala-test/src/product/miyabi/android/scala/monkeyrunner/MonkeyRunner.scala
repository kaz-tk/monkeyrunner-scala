package product.miyabi.android.scala.monkeyrunner
import com.android.chimpchat.ChimpChat
import java.util.logging.Logger
import java.lang.Long
import com.android.chimpchat.core.IChimpDevice
import com.android.chimpchat.core.ChimpImageBase
import com.android.chimpchat.core.IChimpImage
import javax.swing.JOptionPane
import com.android.monkeyrunner.MonkeyRunnerOptions
import java.util.TreeMap

class MonkeyRunner{
  
    //var LOG:Logger = Logger.getLogger(MonkeyRunner.toString());


}

object MonkeyRunner extends MonkeyRunner{
    private var chimpchat:ChimpChat = null
    
    /**
     * @author Kazushige TAKEUCHI
     * @description To Init MonkeyRunner EnvironMent 
     * @args{ args }
     */
    def init(args:Array[String]){
   	        var options:MonkeyRunnerOptions = MonkeyRunnerOptions.processOptions(args);

			var chimp_options:TreeMap[String, String]  = new TreeMap[String, String]();
			chimp_options.put("backend", options.getBackendName());
	        var chimp:ChimpChat = ChimpChat.getInstance(chimp_options);

	        MonkeyRunner.setChimpChat(chimp);

    }
    
    def setChimpChat(chimp:ChimpChat){
        chimpchat = chimp;
    }

    def waitForConnection(
    		regexDeviceName:String =".*",
    		timeoutMs:Long = Long.MAX_VALUE
    		):MonkeyDevice = {
        var device:IChimpDevice = null
        device = chimpchat.waitForConnection(
        								timeoutMs,
                						regexDeviceName);
        return new MonkeyDevice(device);
    }

    def sleep(ms:Long) {

        try {
            Thread.sleep(ms);
        } catch {case e:InterruptedException =>
            //LOG.log(Level.SEVERE, "Error sleeping", e);
        }
    }

/*    def help(format:String="text") {
        return MonkeyRunnerHelp.helpString(format);
    }
*/

    def input(message:String,initialValue:String ="",title:String="Input") {
        return input(message, initialValue, title);
    }

    //def choice(message:String,choices:CollectionCollection<String>,title:String="Input") {
    //    return choice(message, title, choices);
    //}

/*    def loadImageFromFile(path:String):MonkeyImage {
        val image:IChimpImage = ChimpImageBase.loadImageFromFile(path);
        return new MonkeyImage(image);
    }*/
/*
    def alert(message:String,title:String ="Alart",buttonTitle:String="OK") {
        Object[] options = { okTitle };
        JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
*/    
    
/*    public static int choice(String message, String title, Collection<String> choices) {
        Object[] possibleValues = choices.toArray();
        Object selectedValue = JOptionPane.showInputDialog(null, message, title,
                JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);

        for (int x = 0; x < possibleValues.length; x++) {
            if (possibleValues[x].equals(selectedValue)) {
                return x;
            }
        }
        // Error
        return -1;
    }
*/
    /**
     * Display a dialog that allows the user to input a text string.
     *
     * @param message the message to show.
     * @param initialValue the initial value to display in the dialog
     * @param title the title of the dialog box.
     * @return the entered string, or null if cancelled
     */
/*    def input(message:String , initialValue:String , title:String ):String {
        return (String) JOptionPane.showInputDialog(null, message, title,
                JOptionPane.QUESTION_MESSAGE, null, null, initialValue);
    }*/
}
