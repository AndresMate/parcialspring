package edu.uptc.parcialspring.controller;

import edu.uptc.parcialspring.entities.Sale;
import edu.uptc.parcialspring.service.SaleService;
import edu.uptc.parcialspring.handling.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return ResponseHandler.generateResponse("Sales retrieved successfully", HttpStatus.OK, sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(value -> ResponseHandler.generateResponse("Sale found", HttpStatus.OK, value))
                .orElseGet(() -> ResponseHandler.generateResponse("Sale not found", HttpStatus.NOT_FOUND, null));
    }

    @PostMapping
    public ResponseEntity<Object> saveSale(@RequestBody Sale sale) {
        Sale savedSale = saleService.saveSale(sale);
        return ResponseHandler.generateResponse("Sale created successfully", HttpStatus.CREATED, savedSale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSale(@PathVariable Long id) {
        boolean deleted = saleService.deleteSale(id);
        if (deleted) {
            return ResponseHandler.generateResponse("Sale deleted successfully", HttpStatus.NO_CONTENT, null);
        } else {
            return ResponseHandler.generateResponse("Sale not found", HttpStatus.NOT_FOUND, null);
        }
    }
}