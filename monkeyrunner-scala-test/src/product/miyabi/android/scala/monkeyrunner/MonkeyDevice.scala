package product.miyabi.android.scala.monkeyrunner

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.common.base.Functions
import com.google.common.base.Preconditions
import com.google.common.collect.Collections2
import com.android.chimpchat.ChimpChat
import com.android.chimpchat.core.By
import com.android.chimpchat.core.IChimpView
import com.android.chimpchat.core.IChimpDevice
import com.android.chimpchat.core.IChimpImage
import com.android.chimpchat.core.TouchPressType
import com.android.chimpchat.hierarchyviewer.HierarchyViewer
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Map
import java.util.logging.Logger
import scala.collection.JavaConversions
import java.util.HashMap

//import product.miyabi.android.scala.monkeyrunner.doc.MonkeyRunnerExported;

/*
 * Abstract base class that represents a single connected Android
 * Device and provides MonkeyRunner API methods for interacting with
 * that device.  Each backend will need to create a concrete
 * implementation of this class.
 */
//@MonkeyRunnerExported(doc = "Represents a device attached to the system.")
class MonkeyDevice(newimpl:IChimpDevice ){
    //val LOG:Logger = Logger.getLogger(MonkeyDevice.class.getName());


    //@MonkeyRunnerExported(doc = "Sends a DOWN event when used with touch() or press().")
    final val DOWN = TouchPressType.DOWN.getIdentifier();

    //@MonkeyRunnerExported(doc = "Sends an UP event when used with touch() or press().")
    final val UP:String  = TouchPressType.UP.getIdentifier();

    //@MonkeyRunnerExported(doc = "Sends a DOWN event, immediately followed by an UP event when used with touch() or press()")
    final val DOWN_AND_UP:String = TouchPressType.DOWN_AND_UP.getIdentifier();

    var impl:IChimpDevice=null
    impl = newimpl;

    def getHierarchyViewer():HierarchyViewer = {
        return impl.getHierarchyViewer();
    }

    def  takeSnapshot():MonkeyImage = {
      
        var image:IChimpImage = null
        image = impl.takeSnapshot();
        return new MonkeyImage(image);
    }

    def getProperty(key:String):String = {
        return impl.getProperty(key);
    }

    //@MonkeyRunnerExported(doc = "Synonym for getProperty()",
    //        args = {"key"},
    //        argDocs = {"The name of the system variable."},
    //        returns = "The variable's value.")
    def getSystemProperty(key:String):String = {
        impl.getSystemProperty(key);
    }

    //@MonkeyRunnerExported(doc = "Sends a touch event at the specified location",
    //        args = { "x", "y", "type" },
    //        argDocs = { "x coordinate in pixels",
    //                    "y coordinate in pixels",
    //                    "touch event type as returned by TouchPressType()"})
    def touch(x:Int,y:Int,touchType:TouchPressType=TouchPressType.DOWN_AND_UP)/*:Void*/ {

        impl.touch(x, y, touchType);
    }

    //@MonkeyRunnerExported(doc = "Simulates dragging (touch, hold, and move) on the device screen.",
    //        args = { "start", "end", "duration", "steps"},
    //        argDocs = { "The starting point for the drag (a tuple (x,y) in pixels)",
    //        "The end point for the drag (a tuple (x,y) in pixels",
    //        "Duration of the drag in seconds (default is 1.0 seconds)",
    //        "The number of steps to take when interpolating points. (default is 10)"})
    def drag(start:TouchRect,end:TouchRect,ms:Long=1000,steps:Int=10){
        impl.drag(start.x, start.y, end.x, end.y, steps, ms);
    }

    //@MonkeyRunnerExported(doc = "Send a key event to the specified key",
    //        args = { "name", "type" },
    //        argDocs = { "the keycode of the key to press (see android.view.KeyEvent)",
    //        "touch event type as returned by TouchPressType(). To simulate typing a key, " +
    //        "send DOWN_AND_UP"})
    def press(name:String,touchType:TouchPressType=TouchPressType.DOWN_AND_UP){
        impl.press(name, touchType);
    }

    //@MonkeyRunnerExported(doc = "Types the specified string on the keyboard. This is " +
    //        "equivalent to calling press(keycode,DOWN_AND_UP) for each character in the string.",
    //        args = { "message" },
    //        argDocs = { "The string to send to the keyboard." })
/*    def type(PyObject[] args, String[] kws):Void  {
        ArgParser ap = JythonUtils.createArgParser(args, kws);
        Preconditions.checkNotNull(ap);

        String message = ap.getString(0);
        impl.type(message);
    }
*/
    //@MonkeyRunnerExported(doc = "Executes an adb shell command and returns the result, if any.",
    //        args = { "cmd"},
    //        argDocs = { "The adb shell command to execute." },
    //        returns = "The output from the command.")
    def shell(cmd:String)/*:String*/ {
    	impl.shell(cmd)
    }

    //@MonkeyRunnerExported(doc = "Reboots the specified device into a specified bootloader.",
    //        args = { "into" },
    //        argDocs = { "the bootloader to reboot into: bootloader, recovery, or None"})
    def reboot(into:String=null) {
                impl.reboot(into);
    }

    //@MonkeyRunnerExported(doc = "Installs the specified Android package (.apk file) " +
    //        "onto the device. If the package already exists on the device, it is replaced.",
    //        args = { "path" },
    //        argDocs = { "The package's path and filename on the host filesystem." },
    //        returns = "True if the install succeeded")
    def installPackage(path:String)/*:Boolean*/ {
        /*return*/
    	impl.installPackage(path);
    }

    //@MonkeyRunnerExported(doc = "Deletes the specified package from the device, including its " +
    //        "associated data and cache.",
    //        args = { "package"},
    //        argDocs = { "The name of the package to delete."},
    //        returns = "True if remove succeeded")
    def removePackage(packageName:String)/*:Boolean*/ {
        /*return */
    	impl.removePackage(packageName);
    }

    /*@MonkeyRunnerExported(doc = "Starts an Activity on the device by sending an Intent " +
            "constructed from the specified parameters.",
            args = { "uri", "action", "data", "mimetype", "categories", "extras",
                     "component", "flags" },
            argDocs = { "The URI for the Intent.",
                        "The action for the Intent.",
                        "The data URI for the Intent",
                        "The mime type for the Intent.",
                        "A Python iterable containing the category names for the Intent.",
                        "A dictionary of extras to add to the Intent. Types of these extras " +
                        "are inferred from the python types of the values.",
                        "The component of the Intent.",
                        "An iterable of flags for the Intent." +
                        "All arguments are optional. The default value for each argument is null." +
                        "(see android.content.Intent)"})

     */
    def startActivity(
    		uri:String=null,
    		action:String=null,
    		data:String=null,
    		mimetype:String=null,
    		categories:Collection[String],
    		extras:HashMap[String,Object],
    		component:String=null,
    		flags:Int=0
    			)/*:Void*/ {

        impl.startActivity(uri, action, data, mimetype, categories, extras, component, flags);
    }

    /*@MonkeyRunnerExported(doc = "Sends a broadcast intent to the device.",
            args = { "uri", "action", "data", "mimetype", "categories", "extras",
                     "component", "flags" },
                     argDocs = { "The URI for the Intent.",
                             "The action for the Intent.",
                             "The data URI for the Intent",
                             "The mime type for the Intent.",
                             "An iterable of category names for the Intent.",
                             "A dictionary of extras to add to the Intent. Types of these extras " +
                             "are inferred from the python types of the values.",
                             "The component of the Intent.",
                             "An iterable of flags for the Intent." +
                             "All arguments are optional. " + "" +
                             "The default value for each argument is null." +
                             "(see android.content.Context.sendBroadcast(Intent))"})
                             */
    def broadcastIntent(
    		uri:String=null,
    		action:String=null,
    		data:String=null,
    		mimetype:String=null,
    		categories:Collection[String],
    		extras:HashMap[String,Object],
    		component:String=null,
    		flags:Int =0
    		)/*:Void*/ {

        impl.broadcastIntent(uri, action, data, mimetype, categories, extras, component, flags);
    }

/*    @MonkeyRunnerExported(doc = "Run the specified package with instrumentation and return " +
            "the output it generates. Use this to run a test package using " +
            "InstrumentationTestRunner.",
            args = { "className", "args" },
            argDocs = { "The class to run with instrumentation. The format is " +
                        "packagename/classname. Use packagename to specify the Android package " +
                        "to run, and classname to specify the class to run within that package. " +
                        "For test packages, this is usually " +
                        "testpackagename/InstrumentationTestRunner",
                        "A map of strings to objects containing the arguments to pass to this " +
                        "instrumentation (default value is None)." },
            returns = "A map of strings to objects for the output from the package. " +
                      "For a test package, contains a single key-value pair: the key is 'stream' " +
                      "and the value is a string containing the test output.")

*/  def instrument(
			packageName:String ,
			instrumentArgs:HashMap[String,Object]=null
			) {

		var args:Map[String,Object]=null
        if (instrumentArgs == null) {
            args = Collections.emptyMap();
        }else{
        	args=instrumentArgs
        }
        impl.instrument(packageName, args);
    }

    /*@MonkeyRunnerExported(doc = "Wake up the screen on the device")*/
    def wake() {
        impl.wake();
    }


    /*@MonkeyRunnerExported(doc = "Retrieve the properties that can be queried")*/
    def getPropertyList() {
        impl.getPropertyList();
    }

/*    @MonkeyRunnerExported(doc = "Retrieve the view ids for the current application")
    def getViewIdList() {
        impl.getViewIdList();
    }

     //Because the pythonic way is to have flatter hierarchies, rather than doing the
     //findView(By.id("foo")) style the java code uses, I'm going to expose them as individual
     //method calls. This is similar to WebDriver's python bindings.
    @MonkeyRunnerExported(doc = "Obtains the view with the specified id.",
                          args = {"id"},
                          argDocs = {"The id of the view to retrieve."},
                          returns = "The view object with the specified id.")
    def getViewById(id:String):MonkeyView {
        var view:IChimpView =null
        view = impl.getView(By.id(id));
        return new MonkeyView(view);
    }

*/    /*@MonkeyRunnerExported(doc = "Obtains the view with the specified accessibility ids.",
                          args = {"windowId", "accessibility id"},
                          argDocs = {"The window id of the view to retrieve.",
                                     "The accessibility id of the view to retrieve."},
                          returns = "The view object with the specified id.")*/
/*    def getViewByAccessibilityIds(
    		windowId:Int,
    		accessibilityId:Int
    		):MonkeyView {
        var view:IChimpView=null
        view = impl.getView(By.accessibilityIds(windowId, accessibilityId));
        new MonkeyView(view);
    }

    @MonkeyRunnerExported(doc = "Obtains current root view",
                          returns = "The root view object")
    def getRootView():MonkeyView  {
        return
        new MonkeyView(impl.getRootView());
    }

    @MonkeyRunnerExported(doc = "Obtains a list of views that contain the specified text.",
                          args = {"text"},
                          argDocs = {"The text to search for"},
                          returns = "A list of view objects that contain the specified text.")
    def getViewsByText(text:String) {
    	impl.getViews(By.text(text));
        Collection<IChimpView> views = 
          impl.getViews(By.text(text));
        PyList pyViews = new PyList();
        for (IChimpView view : views) {
            pyViews.append(new MonkeyView(view));
        }
        return pyViews;
    }
*/}
