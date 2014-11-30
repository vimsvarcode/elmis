/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */
package org.openlmis.web.controller;

import lombok.NoArgsConstructor;
import org.openlmis.core.exception.DataException;
import org.openlmis.vaccine.domain.*;
import org.openlmis.vaccine.dto.ReceiveVaccine;
import org.openlmis.vaccine.service.*;
import org.openlmis.web.response.OpenLmisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

import static org.openlmis.web.response.OpenLmisResponse.success;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * This controller handles endpoints to Vaccine related features
 */
@Controller
@NoArgsConstructor
@RequestMapping(value="/vaccine")
public class VaccineController extends BaseController {

   @Autowired
   private VaccineTargetService vaccineTargetService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private VaccineDistributionBatchService distributionBatchService;

    @Autowired
    private DistributionLineItemService distributionLineItemService;

    @Autowired
    private StatusService statusService;

    @RequestMapping(value = "/target/create", method = POST, headers = ACCEPT_JSON)
    // TODO: Add appropriate permission
    //  @PreAuthorize("@permissionEvaluator.hasPermission(principal,'MANAGE_VACCINE')")
    public ResponseEntity insert(@RequestBody VaccineTarget vaccineTarget, HttpServletRequest request) {

        vaccineTarget.setCreatedBy(loggedInUserId(request));
        vaccineTarget.setModifiedBy(loggedInUserId(request));

        ResponseEntity<OpenLmisResponse> response;

        try {
            vaccineTargetService.updateVaccineTarget(vaccineTarget);
        } catch(DuplicateKeyException exp){
        return OpenLmisResponse.error("There is a record with the same geographic zone and target year.", HttpStatus.BAD_REQUEST);
      }

        response = success(messageService.message("message.vaccine.target.created.success"));
        response.getBody().addData("vaccineTarget", vaccineTarget);
        return response;
    }

    @RequestMapping(value = "/target/delete/{id}", method = DELETE, headers = ACCEPT_JSON)
    // TODO: Add appropriate permission
    //  @PreAuthorize("@permissionEvaluator.hasPermission(principal,'MANAGE_VACCINE')")
    public ResponseEntity deleteVaccineTarget(@PathVariable(value="id") Long id){

        vaccineTargetService.deleteVaccineTarget(id);

        ResponseEntity<OpenLmisResponse> response;
        response = success(messageService.message("message.vaccine.target.created.success"));
        return response;
    }

    @RequestMapping(value = "/target/list", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity getVaccineTargets() {

        return OpenLmisResponse.response("vaccineTargets", vaccineTargetService.getVaccineTargets());
    }

    @RequestMapping(value = "/target/get/{id}", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity getVaccineTarget(@PathVariable(value="id") Long id){
        return OpenLmisResponse.response("vaccineTarget", vaccineTargetService.getVaccineTarget(id));
    }

   /* @RequestMapping(value = "/distribution-batches/dispatch/{dispatchId}", method = GET, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal, 'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity<OpenLmisResponse> getDistributionBatchesByBatchNumber(@PathVariable("dispatchId") String dispatchId){
        return OpenLmisResponse.response("distributionBatches", distributionBatchService.getByDispatchId(dispatchId));
    }
*/
   /* @RequestMapping(value = "/distribution-batches", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> searchDistributions(@RequestParam(required = true) String param) {
        return OpenLmisResponse.response("distributionBatches", distributionBatchService.searchDistributionBatches(param));
    }*/

   /* @RequestMapping(value = "/distribution-batches/{id}", method = GET, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal, 'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity<OpenLmisResponse> getDistributionBatchById(@PathVariable("id") Long id){
        return OpenLmisResponse.response("distributionBatch", distributionBatchService.getById(id));
    }*/

    @RequestMapping(value = "/usable-batches/product/{productId}", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> getUsableBatches(@PathVariable("productId") Long productId){
        return OpenLmisResponse.response("usableBatches", distributionBatchService.getUsableBatches(productId));
    };

    @RequestMapping(value = "/receive-vaccine", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> searchReceivedVaccine(@RequestParam(required = true) Long facilityId) {
        return OpenLmisResponse.response("receivedVaccines", distributionBatchService.getReceivedVaccinesForFacility(facilityId));
    }

    /*@RequestMapping(value = "/distribution-batches", method = POST, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal, 'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity insertDistributionBatches(@RequestBody DistributionBatch distributionBatch, HttpServletRequest request){

        distributionBatch.setCreatedBy(loggedInUserId(request));
        distributionBatch.setModifiedBy(loggedInUserId(request));

        ResponseEntity<OpenLmisResponse> response;

        try {
            distributionBatchService.update(distributionBatch);
        }catch (DataException exception) {
            OpenLmisResponse openLmisResponse = new OpenLmisResponse("distributionBatch", distributionBatch);
            return openLmisResponse.errorEntity(exception, BAD_REQUEST);
        }
        response = success(messageService.message("message.distribution.batch.created.success"));
        response.getBody().addData("distributionBatch", distributionBatch);
        return response;
    }*/

    @RequestMapping(value = "/receive-vaccine", method = POST, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal, 'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity receiveVaccine(@RequestBody ReceiveVaccine receiveVaccine, HttpServletRequest request){

        receiveVaccine.setCreatedBy(loggedInUserId(request));
        receiveVaccine.setModifiedBy(loggedInUserId(request));

        ResponseEntity<OpenLmisResponse> response;
        InventoryTransaction inventoryTransaction = null;
        List<InventoryBatch> inventoryBatches = null;

        try {
            inventoryTransaction = receiveVaccine.getInventoryTransaction();
            inventoryBatches  = receiveVaccine.getInventoryBatches();

            distributionBatchService.receiveVaccine(inventoryTransaction, inventoryBatches);

        }catch (DataException exception) {
            OpenLmisResponse openLmisResponse = new OpenLmisResponse("receiveVaccine", receiveVaccine);
            return openLmisResponse.errorEntity(exception, BAD_REQUEST);
        }
        response = success(messageService.message("message.receive.vaccine.created.success"));
        receiveVaccine.setInventoryTransaction(inventoryTransaction);
        receiveVaccine.setInventoryBatches(inventoryBatches);
        response.getBody().addData("receiveVaccine", receiveVaccine);
        return response;
    }

    /*@RequestMapping(value = "/distribution-batches/{id}", method = PUT, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal,'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity<OpenLmisResponse> update(@PathVariable("id") Long id,
                                                   @RequestBody DistributionBatch distributionBatch,
                                                   HttpServletRequest request) {
        distributionBatch.setId(id);
        distributionBatch.setModifiedBy(loggedInUserId(request));

        try {
            distributionBatchService.update(distributionBatch);
        } catch (DataException exception) {
            OpenLmisResponse openLmisResponse = new OpenLmisResponse("distributionBatch", distributionBatch);
            return openLmisResponse.errorEntity(exception, BAD_REQUEST);
        }

        String successMessage = messageService.message("message.distribution.batch.updated.success");
        OpenLmisResponse openLmisResponse = new OpenLmisResponse("distributionBatch", distributionBatch);
        return openLmisResponse.successEntity(successMessage);
    }*/

   /* @RequestMapping(value = "/distribution-batch-line-items", method = POST, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal, 'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity insertDistributionLinetItems(@RequestBody DistributionLineItem distributionLineItem, HttpServletRequest request){

        distributionLineItem.setCreatedBy(loggedInUserId(request));
        distributionLineItem.setModifiedBy(loggedInUserId(request));

        ResponseEntity<OpenLmisResponse> response;

        try {
            distributionLineItemService.update(distributionLineItem);
        }catch (DataException exception) {
            OpenLmisResponse openLmisResponse = new OpenLmisResponse("distributionLineItem", distributionLineItem);
            return openLmisResponse.errorEntity(exception, BAD_REQUEST);
        }
        response = success(messageService.message("message.distribution.batch.line.item.created.success"));
        response.getBody().addData("distributionLineItem", distributionLineItem);
        return response;
    }
*/
   /* @RequestMapping(value = "/distribution-batch-line-items/{id}", method = PUT, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal,'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity<OpenLmisResponse> updateDispatchLineItem(@PathVariable("id") Long id,
                                                   @RequestBody DistributionLineItem distributionLineItem,
                                                   HttpServletRequest request) {
        distributionLineItem.setId(id);
        distributionLineItem.setModifiedBy(loggedInUserId(request));

        try {
            distributionLineItemService.update(distributionLineItem);
        } catch (DataException exception) {
            OpenLmisResponse openLmisResponse = new OpenLmisResponse("distributionLineItem", distributionLineItem);
            return openLmisResponse.errorEntity(exception, BAD_REQUEST);
        }

        String successMessage = messageService.message("message.distribution.batch.line.item.updated.success");
        OpenLmisResponse openLmisResponse = new OpenLmisResponse("distributionLineItem", distributionLineItem);
        return openLmisResponse.successEntity(successMessage);
    }

    @RequestMapping(value = "/distribution-batch-line-items", method = GET, headers = ACCEPT_JSON)
    //@PreAuthorize("@permissionEvaluator.hasPermission(principal, 'MANAGE_VACCINE_DISTRIBUTION_BATCH')")
    public ResponseEntity<OpenLmisResponse> getAllDistributionLineItems(){
        return OpenLmisResponse.response("distributionLineItems", distributionLineItemService.getAll());
    }

    @RequestMapping(value = "/distribution-batches/filter", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> filterDistributionBatches(HttpServletRequest request){

        return OpenLmisResponse.response("distributionBatches", distributionBatchService.filterDistributionBatches(request.getParameterMap()));
    }
*/

    @RequestMapping(value = "/manufacturers", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> getManufacturers(){
        return OpenLmisResponse.response("manufacturers", manufacturerService.getAll());
    }

    @RequestMapping(value = "/distributionTypes", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> getDistributionTypes(){
        return OpenLmisResponse.response("distributionTypes", DistributionType.values());
    }


    @RequestMapping(value = "/status", method = GET, headers = ACCEPT_JSON)
    public ResponseEntity<OpenLmisResponse> getVaccineDistributionStatus(){
        return OpenLmisResponse.response("status", statusService.getAll());
    }


}
