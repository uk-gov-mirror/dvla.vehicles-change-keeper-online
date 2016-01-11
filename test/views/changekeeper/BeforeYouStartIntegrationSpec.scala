package views.changekeeper

import composition.TestHarness
import helpers.CookieFactoryForUISpecs
import helpers.UiSpec
import models.K2KCacheKeyPrefix.CookiePrefix
import models.VehicleLookupFormModel.VehicleLookupFormModelCacheKey
import pages.changekeeper.BeforeYouStartPage
import pages.changekeeper.BeforeYouStartPage.startNow
import pages.changekeeper.VehicleLookupPage
import pages.common.Feedback.EmailFeedbackLink
import uk.gov.dvla.vehicles.presentation.common
import common.model.PrivateKeeperDetailsFormModel.privateKeeperDetailsCacheKey
import common.model.VehicleAndKeeperDetailsModel.vehicleAndKeeperLookupDetailsCacheKey
import common.testhelpers.UiTag
import org.scalatest.selenium.WebBrowser.{click, go, pageTitle, pageSource}

class BeforeYouStartIntegrationSpec extends UiSpec with TestHarness {


  "go to page" should {
    "display the page" taggedAs UiTag in new WebBrowserForSelenium {
      go to BeforeYouStartPage
      pageTitle should equal(BeforeYouStartPage.title)
    }

    "contain feedback email facility with appropriate subject" taggedAs UiTag in new WebBrowserForSelenium {
      go to BeforeYouStartPage
      pageSource.contains(EmailFeedbackLink) should equal(true)
    }

    "clear all cookies stored in cache" taggedAs UiTag in new PhantomJsByDefault {
      go to BeforeYouStartPage
      CookieFactoryForUISpecs.vehicleLookupFormModel()
      CookieFactoryForUISpecs.vehicleAndKeeperDetails()
      CookieFactoryForUISpecs.privateKeeperDetails()

      go to BeforeYouStartPage

      webDriver.manage().getCookieNamed(VehicleLookupFormModelCacheKey) should equal(null)
      webDriver.manage().getCookieNamed(vehicleAndKeeperLookupDetailsCacheKey) should equal(null)
      webDriver.manage().getCookieNamed(privateKeeperDetailsCacheKey) should equal(null)
    }
  }

  "startNow button" should {
    "go to next page" taggedAs UiTag in new WebBrowserForSelenium {
      go to BeforeYouStartPage
      click on startNow
      pageTitle should equal(VehicleLookupPage.title)
    }
  }
}
