/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2013 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.rnr.repository;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.db.categories.UnitTests;
import org.openlmis.rnr.domain.RequisitionStatusChange;
import org.openlmis.rnr.repository.mapper.RequisitionStatusChangeMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@Category(UnitTests.class)
public class RequisitionStatusChangeRepositoryTest {

  @InjectMocks
  RequisitionStatusChangeRepository repository;

  @Mock
  RequisitionStatusChangeMapper mapper;

  @Test
  public void shouldGetByRnrId() throws Exception {
    List<RequisitionStatusChange> requisitionStatusChanges = new ArrayList<>();
    when(mapper.getByRnrId(2L)).thenReturn(requisitionStatusChanges);

    List<RequisitionStatusChange> changes = repository.getByRnrId(2L);

    assertThat(changes, is(requisitionStatusChanges));
  }
}
