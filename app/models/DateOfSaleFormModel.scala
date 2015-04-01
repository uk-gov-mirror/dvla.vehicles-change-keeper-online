package models

import org.joda.time.LocalDate
import play.api.data.Forms.mapping
import play.api.data.validation.{Valid, ValidationError, Invalid, Constraint}
import play.api.i18n.Messages
import play.api.libs.json.Json
import uk.gov.dvla.vehicles.presentation.common.clientsidesession.CacheKey
import uk.gov.dvla.vehicles.presentation.common.mappings.Date.{dateMapping, notInTheFuture, notBefore}
import uk.gov.dvla.vehicles.presentation.common.mappings.Mileage.mileage
import uk.gov.dvla.vehicles.presentation.common.services.DateService
import models.K2KCacheKeyPrefix.CookiePrefix

case class DateOfSaleFormModel(mileage: Option[Int], dateOfSale: LocalDate)

object DateOfSaleFormModel {
  implicit val JsonFormat = Json.format[DateOfSaleFormModel]
  final val DateOfSaleCacheKey = s"${CookiePrefix}dateOfSale"
  implicit val Key = CacheKey[DateOfSaleFormModel](DateOfSaleCacheKey)

  object Form {

    def notOne(message: String = Messages("error.date.notOne"),
                  name: String = "constraint.notOne" ) = Constraint[LocalDate](name) {
      case d: LocalDate =>
        if (d.getYear ==  0) Invalid(ValidationError(message))
        else Valid
    }

    final val MileageId = "mileage"
    final val TodaysDateId = "todays_date"
    final val DateOfSaleId = "dateofsale"

    final def detailMapping(implicit dateService: DateService) = mapping(
      MileageId -> mileage,
      DateOfSaleId -> dateMapping.verifying(notInTheFuture())
        .verifying(notOne())
    )(DateOfSaleFormModel.apply)(DateOfSaleFormModel.unapply)
  }
}