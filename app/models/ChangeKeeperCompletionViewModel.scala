package models

import play.api.libs.json.Json
import uk.gov.dvla.vehicles.presentation.common.clientsidesession.CacheKey
import uk.gov.dvla.vehicles.presentation.common.model.VehicleAndKeeperDetailsModel
import models.K2KCacheKeyPrefix.CookiePrefix

final case class ChangeKeeperCompletionViewModel(vehicleAndKeeperDetails: VehicleAndKeeperDetailsModel,
                                                 newKeeperDetails: NewKeeperDetailsViewModel,
                                                 completeAndConfirmDetails: CompleteAndConfirmFormModel,
                                                 completeAndConfirmResponseModel: CompleteAndConfirmResponseModel)

object ChangeKeeperCompletionViewModel {
  implicit val JsonFormat = Json.format[CompleteAndConfirmResponseModel]
  final val AcquireCompletionCacheKey = s"${CookiePrefix}acquireCompletion"
  implicit val Key = CacheKey[CompleteAndConfirmResponseModel](AcquireCompletionCacheKey)
}
