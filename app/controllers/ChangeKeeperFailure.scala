package controllers

import com.google.inject.Inject
import models.AllCacheKeys
import models.ChangeKeeperCompletionViewModel
import models.CompleteAndConfirmFormModel
import models.CompleteAndConfirmResponseModel
import models.DateOfSaleFormModel
import models.K2KCacheKeyPrefix.CookiePrefix
import models.VehicleNewKeeperCompletionCacheKeys
import play.api.mvc.{Action, Controller, Request}
import uk.gov.dvla.vehicles.presentation.common
import common.clientsidesession.ClientSideSessionFactory
import common.clientsidesession.CookieImplicits.{RichCookies, RichResult}
import common.LogFormats.DVLALogger
import common.model.{NewKeeperDetailsViewModel, TraderDetailsModel, VehicleAndKeeperDetailsModel}
import utils.helpers.Config

class ChangeKeeperFailure @Inject()()(implicit clientSideSessionFactory: ClientSideSessionFactory,
                                       config: Config) extends Controller with DVLALogger {

  private final val MissingCookiesAcquireFailure = "Missing cookies in cache. Acquire was failed, however cannot " +
    "display failure page. Redirecting to BeforeYouStart"
  private final val MissingCookies = "Missing cookies in cache."

  def present = Action { implicit request =>
    val result = for {
      vehicleAndKeeperDetailsModel <- request.cookies.getModel[VehicleAndKeeperDetailsModel]
      newKeeperDetailsModel <- request.cookies.getModel[NewKeeperDetailsViewModel]
      completeAndConfirmModel <- request.cookies.getModel[CompleteAndConfirmFormModel]
      dateOfSaleFormModel <- request.cookies.getModel[DateOfSaleFormModel]
      responseModel <- request.cookies.getModel[CompleteAndConfirmResponseModel]
    } yield {
      logMessage(request.cookies.trackingId(), Info, "Presenting change keeper failure view")
      Ok(views.html.changekeeper.change_keeper_failure(ChangeKeeperCompletionViewModel(
        vehicleAndKeeperDetailsModel, newKeeperDetailsModel, dateOfSaleFormModel, completeAndConfirmModel, responseModel
      )))
    }
    result getOrElse
      redirectToStart(MissingCookiesAcquireFailure)
  }

  def buyAnother = Action { implicit request =>
    val result = for {
      traderDetails <- request.cookies.getModel[TraderDetailsModel]
    } yield Redirect(routes.VehicleLookup.present())
        .discardingCookies(VehicleNewKeeperCompletionCacheKeys)
    result getOrElse redirectToStart(MissingCookies)
  }

  def finish = Action { implicit request =>
    logMessage(request.cookies.trackingId(), Debug, s"Redirecting to ${routes.BeforeYouStart.present()}")
    Redirect(routes.BeforeYouStart.present())
        .discardingCookies(AllCacheKeys)
  }

  private def redirectToStart(message: String)(implicit request: Request[_]) = {
    logMessage(request.cookies.trackingId(), Warn, message)
    Redirect(routes.BeforeYouStart.present())
  }
}