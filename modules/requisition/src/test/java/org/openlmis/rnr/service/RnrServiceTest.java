package org.openlmis.rnr.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.core.builder.ProductBuilder;
import org.openlmis.core.domain.Product;
import org.openlmis.core.service.ProductService;
import org.openlmis.rnr.domain.Rnr;
import org.openlmis.rnr.repository.RnrRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openlmis.core.builder.FacilityBuilder.FACILITY_CODE;

@RunWith(MockitoJUnitRunner.class)
public class RnrServiceTest {

    public static final String HIV = "HIV";

    @Autowired
    private RnrService rnrService;
    @Mock
    private ProductService productService;
    @Mock
    private RnrRepository rnrRepository;

    @Before
    public void setup() {
        rnrService = new RnrService(rnrRepository, productService);
    }

    @Test
    public void shouldInitRequisition() {
        when(rnrRepository.getRequisitionByFacilityAndProgram(FACILITY_CODE, HIV)).thenReturn(new Rnr());
        List<Product> products = new ArrayList<>();
        products.add(make(a(ProductBuilder.product)));
        when(productService.getByFacilityAndProgram(FACILITY_CODE, HIV)).thenReturn(products);
        Rnr rnr = rnrService.initRnr(FACILITY_CODE, HIV, "user");
        verify(productService).getByFacilityAndProgram(FACILITY_CODE, HIV);
        verify(rnrRepository).insert(rnr);
        assertThat(rnr.getLineItems().size(), is(1));
    }


    @Test
    public void shouldReturnExistingRnrIfAlreadyInitiated() {
        Rnr initiatedRnr = new Rnr();
        initiatedRnr.setId(1);
        when(rnrRepository.getRequisitionByFacilityAndProgram(FACILITY_CODE, HIV)).thenReturn(initiatedRnr);
        Rnr rnr = rnrService.initRnr(FACILITY_CODE, HIV, "user");
        verify(productService, never()).getByFacilityAndProgram(FACILITY_CODE, HIV);
        verify(rnrRepository, never()).insert(rnr);
        assertThat(rnr, is(initiatedRnr));
    }
}
