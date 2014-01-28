/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

function FacilityVisit(facilityVisitJson) {
  $.extend(true, this, facilityVisitJson);
  var mandatoryList = ['observations', 'verifiedBy', 'confirmedBy'];

  FacilityVisit.prototype.computeStatus = function (useFacilityVisitedFlag) {
    if (!useFacilityVisitedFlag)
      return computeStatusForGeneralObservation.call(this);

    if (this.visited === null || this.visited === undefined)
      return DistributionStatus.EMPTY;

    if (!this.visitDate)
      return DistributionStatus.INCOMPLETE;

    return computeStatusForGeneralObservation.call(this) === DistributionStatus.COMPLETE ? DistributionStatus.COMPLETE : DistributionStatus.INCOMPLETE;
  };

  function computeStatusForGeneralObservation() {
    var status;
    var _this = this;

    function isValid(fieldName) {
      if (!_this[fieldName]) return false;

      if (fieldName === 'observations') return !isUndefined(_this[fieldName]);

      return !(isUndefined(_this[fieldName].name) || isUndefined(_this[fieldName].title));
    }

    function isEmpty(fieldName) {
      if (!_this[fieldName]) return true;

      if (fieldName === 'observations') return isUndefined(_this[fieldName]);

      return isUndefined(_this[fieldName].name) && isUndefined(_this[fieldName].title);
    }

    $(mandatoryList).each(function (i, fieldName) {
      if (isValid(fieldName) && (status == DistributionStatus.COMPLETE || !status)) {
        status = DistributionStatus.COMPLETE;
      } else if (!isValid(fieldName) && isEmpty(fieldName) && (!status || status == DistributionStatus.EMPTY)) {
        status = DistributionStatus.EMPTY;
      } else if ((!isValid(fieldName) && status === DistributionStatus.COMPLETE) || (isValid(fieldName) && status === DistributionStatus.EMPTY) || (!isEmpty(fieldName))) {
        status = DistributionStatus.INCOMPLETE;
        return false;
      }
      return true;
    });

    this.status = status;

    return this.status;
  }

}