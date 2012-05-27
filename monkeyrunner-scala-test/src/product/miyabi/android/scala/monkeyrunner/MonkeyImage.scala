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
package product.miyabi.android.scala.monkeyrunner;

import com.google.common.base.Preconditions;

import com.android.chimpchat.core.IChimpImage;


import java.util.logging.Logger;

/**
 * Jython object to encapsulate images that have been taken.
 */
//@MonkeyRunnerExported(doc = "An image")
class MonkeyImage(newimpl:IChimpImage){
    //private static Logger LOG = Logger.getLogger(MonkeyImage.class.getCanonicalName());


    private var impl:IChimpImage =null;

    impl = newimpl;

    def getImpl():IChimpImage = {
        return impl;
    }


/*    @MonkeyRunnerExported(doc = "Converts the MonkeyImage into a particular format and returns " +
                                "the result as a String. Use this to get access to the raw" +
                                "pixels in a particular format. String output is for better " +
                                "performance.",
        args = {"format"},
        argDocs = { "The destination format (for example, 'png' for Portable " +
            "Network Graphics format). The default is png." },
        returns = "The resulting image as a String.")*/
    def convertToBytes(format:String="png"):Array[Byte] = {
      
      return impl.convertToBytes(format);
    }

/*    @MonkeyRunnerExported(doc = "Write the MonkeyImage to a file.  If no " +
            "format is specified, this method guesses the output format " +
            "based on the extension of the provided file extension. If it is unable to guess the " +
            "format, it uses PNG.",
            args = {"path", "format"},
            argDocs = {"The output filename, optionally including its path",
                       "The destination format (for example, 'png' for " +
                       " Portable Network Graphics format." },
            returns = "boolean true if writing succeeded.")*/
    def writeToFile(path:String,format:String=null):Boolean = {
        return impl.writeToFile(path, format);
    }

    
    //@ToDo Implement getRawPixel
/*    @MonkeyRunnerExported(doc = "Get a single ARGB (alpha, red, green, blue) pixel at location " +
            "x,y. The arguments x and y are 0-based, expressed in pixel dimensions. X increases " +
            "to the right, and Y increases towards the bottom. This method returns a tuple.",
            args = { "x", "y" },
            argDocs = { "the x offset of the pixel", "the y offset of the pixel" },
            returns = "A tuple of (A, R, G, B) for the pixel. Each item in the tuple has the " +
                      "range 0-255.")*/
    /*public PyObject getRawPixel(PyObject[] args, String[] kws) {
        ArgParser ap = JythonUtils.createArgParser(args, kws);
        Preconditions.checkNotNull(ap);

        int x = ap.getInt(0);
        int y = ap.getInt(1);
        int pixel = impl.getPixel(x, y);
        PyInteger a = new PyInteger((pixel & 0xFF000000) >> 24);
        PyInteger r = new PyInteger((pixel & 0x00FF0000) >> 16);
        PyInteger g = new PyInteger((pixel & 0x0000FF00) >> 8);
        PyInteger b = new PyInteger((pixel & 0x000000FF) >> 0);
        return new PyTuple(a, r, g ,b);
    }
*/
/*    @MonkeyRunnerExported(doc = "Get a single ARGB (alpha, red, green, blue) pixel at location " +
            "x,y. The arguments x and y are 0-based, expressed in pixel dimensions. X increases " +
            "to the right, and Y increases towards the bottom. This method returns an Integer.",
            args = { "x", "y" },
            argDocs = { "the x offset of the pixel", "the y offset of the pixel" },
            returns = "An unsigned integer pixel for x,y. The 8 high-order bits are A, followed" +
                    "by 8 bits for R, 8 for G, and 8 for B.")*/
    def getRawPixelInt(x:Int,y:Int):Int = {
        return impl.getPixel(x, y);
    }

/*    @MonkeyRunnerExported(doc = "Compare this MonkeyImage object to aother MonkeyImage object.",
            args = {"other", "percent"},
            argDocs = {"The other MonkeyImage object.",
                       "A float in the range 0.0 to 1.0, indicating the percentage " +
                       "of pixels that need to be the same for the method to return 'true'. " +
                       "Defaults to 1.0."},
            returns = "boolean 'true' if the two objects contain the same image.")*/
    def sameAs(other:MonkeyImage,percent:Double):Boolean = {
        var otherimpl:IChimpImage  = other.getImpl();

        return impl.sameAs(otherimpl, percent);
    }

/*    @MonkeyRunnerExported(doc = "Copy a rectangular region of the image.",
            args = {"rect"},
            argDocs = {"A tuple (x, y, w, h) describing the region to copy. x and y specify " +
                       "upper lefthand corner of the region. w is the width of the region in " +
                       "pixels, and h is its height."},
            returns = "a MonkeyImage object representing the copied region.")*/
    def getSubImage(rect:Array[Int]):MonkeyImage = {

    	var x:Int =0
    	var y:Int =0
    	var w:Int =0
    	var h:Int =0

        var image:IChimpImage = impl.getSubImage(x, y, w, h);
        return new MonkeyImage(image);
    }
}
