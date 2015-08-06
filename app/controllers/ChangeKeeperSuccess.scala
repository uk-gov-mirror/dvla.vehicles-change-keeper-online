package controllers

import com.google.inject.Inject
import models._
import models.K2KCacheKeyPrefix.CookiePrefix
import play.api.mvc.{Action, Controller, Request}
import uk.gov.dvla.vehicles.presentation.common
import common.clientsidesession.ClientSideSessionFactory
import common.clientsidesession.CookieImplicits.{RichCookies, RichResult}
import common.model.NewKeeperDetailsViewModel
import common.model.VehicleAndKeeperDetailsModel
import uk.gov.dvla.vehicles.presentation.common.LogFormats.DVLALogger
import utils.helpers.Config

class ChangeKeeperSuccess @Inject()()(implicit clientSideSessionFactory: ClientSideSessionFactory,
                                       config: Config,
                                       surveyUrl: SurveyUrl) extends Controller with DVLALogger {

  private final val MissingCookiesSuccess = "Missing cookies in cache. Change of keeper was successful, " +
    "however cannot display success page. Redirecting to BeforeYouStart"

  def present = Action { implicit request =>
    val result = for {
      vehicleAndKeeperDetailsModel <- request.cookies.getModel[VehicleAndKeeperDetailsModel]
      newKeeperDetailsModel <- request.cookies.getModel[NewKeeperDetailsViewModel]
      completeAndConfirmModel <- request.cookies.getModel[CompleteAndConfirmFormModel]
      dateOfSaleFormModel <- request.cookies.getModel[DateOfSaleFormModel]
      responseModel <- request.cookies.getModel[CompleteAndConfirmResponseModel]
    } yield
      Ok(views.html.changekeeper.change_keeper_success(
        ChangeKeeperCompletionViewModel(
          vehicleAndKeeperDetailsModel,
          newKeeperDetailsModel,
          dateOfSaleFormModel,
          completeAndConfirmModel,
          responseModel),
        surveyUrl())
      )

    result getOrElse
      redirectToStart(MissingCookiesSuccess)
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

class SurveyUrl @Inject()(implicit config: Config) {

  def apply(): Option[String] = config.surveyUrl
}