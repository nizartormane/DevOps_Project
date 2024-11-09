package tn.esprit.devops_project.test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.Date;
import java.util.Optional;

class InvoiceServiceTest {

    @InjectMocks
    InvoiceServiceImpl invoiceService;

    @Mock
    InvoiceRepository invoiceRepository;

    @Mock
    SupplierRepository supplierRepository;

    @Mock
    OperatorRepository operatorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRetrieveAllInvoices() {
        invoiceService.retrieveAllInvoices();
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        Invoice retrievedInvoice = invoiceService.retrieveInvoice(invoiceId);
        assertNotNull(retrievedInvoice);
        verify(invoiceRepository, times(1)).findById(invoiceId);
    }

    @Test
    void testCancelInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        invoiceService.cancelInvoice(invoiceId);
        verify(invoiceRepository, times(1)).save(invoice);
        verify(invoiceRepository, times(1)).updateInvoice(invoiceId);
        assertTrue(invoice.getArchived());
    }

    @Test
    void testGetTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();
        invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
        verify(invoiceRepository, times(1)).getTotalAmountInvoiceBetweenDates(startDate, endDate);
    }
}
