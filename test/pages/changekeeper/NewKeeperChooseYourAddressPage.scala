package pages.changekeeper

import helpers.webbrowser.{Element, Page, SingleSel, WebBrowserDSL, WebDriverFactory}
import models.NewKeeperChooseYourAddressFormModel.Form.AddressSelectId
import views.changekeeper.NewKeeperChooseYourAddress
import NewKeeperChooseYourAddress.BackId
import NewKeeperChooseYourAddress.EnterAddressManuallyButtonId
import NewKeeperChooseYourAddress.SelectId
import org.openqa.selenium.WebDriver
import webserviceclients.fakes.FakeAddressLookupWebServiceImpl.UprnValid

object NewKeeperChooseYourAddressPage extends Page with WebBrowserDSL {
  final val address = buildAppUrl("new-keeper-choose-your-address")
  override val url: String = WebDriverFactory.testUrl + address.substring(1)
  final override val title = "Select new keeper address"
  //final val titleCy = "Dewiswch eich cyfeiriad masnach"

//  def chooseAddress(implicit driver: WebDriver): SingleSel = singleSel(id(AddressSelectId))
//
  def back(implicit driver: WebDriver): Element = find(id(BackId)).get
//
//  def manualAddress(implicit driver: WebDriver): Element = find(id(EnterAddressManuallyButtonId)).get
//
//  def getList(implicit driver: WebDriver) = singleSel(id(AddressSelectId)).getOptions
//
//  def getListCount(implicit driver: WebDriver): Int = getList.size
//
//  def select(implicit driver: WebDriver): Element = find(id(SelectId)).get
//
//  def happyPath(implicit driver: WebDriver) = {
//    go to NewKeeperChooseYourAddressPage
//    // HACK for Northern Ireland
////    chooseAddress.value = traderUprnValid.toString
//    chooseAddress.value = "0"
//    click on select
//  }
//
//  def sadPath(implicit driver: WebDriver) = {
//    go to NewKeeperChooseYourAddressPage
//    click on select
//  }
}
