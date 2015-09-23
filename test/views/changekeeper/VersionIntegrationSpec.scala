package views.changekeeper

import composition.TestHarness
import helpers.UiSpec
import scala.io.Source.fromInputStream
import uk.gov.dvla.vehicles.presentation.common.helpers.webbrowser.WebDriverFactory
import org.scalatest.selenium.WebBrowser.{go, pageSource}

class VersionIntegrationSpec extends UiSpec with TestHarness {
  "Version endpoint" should {
    "be declared and should include the build-details.txt from classpath" in new WebBrowserForSelenium {
      go.to(WebDriverFactory.testUrl + s"$applicationContext/version")
      val t = fromInputStream(getClass.getResourceAsStream("/build-details.txt")).getLines().toSet.toList
      pageSource.lines.toSet.toList should contain allOf(t.head, t.tail.head, t.tail.tail.toSeq:_*)
    }
  }
}
