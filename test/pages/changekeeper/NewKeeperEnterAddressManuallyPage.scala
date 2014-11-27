package pages.changekeeper

import helpers.webbrowser.{Element, Page, TextField, WebBrowserDSL, WebDriverFactory}
import views.changekeeper.NewKeeperEnterAddressManually
import NewKeeperEnterAddressManually.{BackId, NextId}
import uk.gov.dvla.vehicles.presentation.common.views.models.AddressLinesViewModel
import org.openqa.selenium.WebDriver
import AddressLinesViewModel.Form.{AddressLinesId, BuildingNameOrNumberId, Line2Id, Line3Id, PostTownId}
import models.NewKeeperEnterAddressManuallyFormModel.Form.AddressAndPostcodeId
import webserviceclients.fakes.FakeAddressLookupService.{BuildingNameOrNumberValid, Line2Valid, Line3Valid, PostTownValid}

object NewKeeperEnterAddressManuallyPage extends Page with WebBrowserDSL {
  final val address = buildAppUrl("new-keeper-enter-address-manually")
  override val url: String = WebDriverFactory.testUrl + address.substring(1)
  final override val title: String = "Enter an address"

  def addressBuildingNameOrNumber(implicit driver: WebDriver): TextField =
    textField(id(s"${AddressAndPostcodeId}_${AddressLinesId}_$BuildingNameOrNumberId"))

  def addressLine2(implicit driver: WebDriver): TextField =
    textField(id(s"${AddressAndPostcodeId}_${AddressLinesId}_$Line2Id"))

  def addressLine3(implicit driver: WebDriver): TextField =
    textField(id(s"${AddressAndPostcodeId}_${AddressLinesId}_$Line3Id"))

  def addressPostTown(implicit driver: WebDriver): TextField =
    textField(id(s"${AddressAndPostcodeId}_${AddressLinesId}_$PostTownId"))

  def next(implicit driver: WebDriver): Element = find(id(NextId)).get

  def back(implicit driver: WebDriver): Element = find(id(BackId)).get

  def errorTextForFields(text:String)(implicit driver: WebDriver):Boolean= find(tagName("body")).get.text.contains(text)

  def happyPath(buildingNameOrNumber: String = BuildingNameOrNumberValid,
                line2: String = Line2Valid,
                line3: String = Line3Valid,
                postTown: String = PostTownValid)
               (implicit driver: WebDriver) = {
    go to NewKeeperEnterAddressManuallyPage
    addressBuildingNameOrNumber.value = buildingNameOrNumber
    addressLine2.value = line2
    addressLine3.value = line3
    addressPostTown.value = postTown
    click on next
  }

  def happyPathMandatoryFieldsOnly(buildingNameOrNumber: String = BuildingNameOrNumberValid,
                                   postTown: String = PostTownValid)
                                  (implicit driver: WebDriver) = {
    go to NewKeeperEnterAddressManuallyPage
    addressBuildingNameOrNumber.value = buildingNameOrNumber
    addressPostTown.value = postTown
    click on next
  }

  def sadPath(implicit driver: WebDriver) = {
    go to NewKeeperEnterAddressManuallyPage
    click on next
  }
}