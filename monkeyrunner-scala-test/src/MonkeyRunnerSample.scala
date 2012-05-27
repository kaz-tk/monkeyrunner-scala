import product.miyabi.android.scala.monkeyrunner.MonkeyRunner
import product.miyabi.android.scala.monkeyrunner.MonkeyDevice
import java.util.TreeMap
import com.android.chimpchat.ChimpChat
import com.android.monkeyrunner.MonkeyRunnerOptions
import product.miyabi.android.scala.monkeyrunner.MonkeyImage

object MonkeyRunnerExec {
	
	def main(args: Array[String]): Unit = {
			println("MonkeyRunner")
			MonkeyRunner.init(args)
			var device:MonkeyDevice = MonkeyRunner.waitForConnection()
			var image1:MonkeyImage = device.takeSnapshot()
			var image2:MonkeyImage = device.takeSnapshot()
			image1.writeToFile("snapshot.png")
			println(image2.sameAs(image1,50))
	}
}