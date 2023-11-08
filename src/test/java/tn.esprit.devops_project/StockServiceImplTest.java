package tn.esprit.devops_project.services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.controllers.StockController;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class StockServiceImplTest {
    @InjectMocks
    private StockController stockController;
    @InjectMocks
    private StockServiceImpl service1;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private StockServiceImpl stockService;
    @Autowired
    private StockServiceImpl service;
    StockServiceImpl stockServiceImpl = Mockito.mock(StockServiceImpl.class);

    @Test
    @Order(1)
    void retrieveAllStocks() {

        List<Stock> retrieveAllStocks = stockController.retrieveAllStock();


        assertNotNull(retrieveAllStocks);
    }
    @Test
    @Order(2)
    void retrieveAllStocks1() {
        List<Stock> mockStocks = new ArrayList<>();
        Stock stock1 = new Stock();

        stock1.setTitle("stock 1");
        Stock stock2 =  new Stock();

        stock2.setTitle("stock 2");
        mockStocks.add(stock1);
        mockStocks.add(stock2);
        StockServiceImpl stockService = Mockito.mock(StockServiceImpl.class);



        when(stockService.retrieveAllStock()).thenReturn(mockStocks);

        StockController stockController = new StockController(stockService);

        List<Stock> retrieveStocks = stockController.retrieveAllStock();

        assertNotNull(retrieveStocks);
        assertEquals(2, retrieveStocks.size());
        assertEquals("stock 1", retrieveStocks.get(0).getTitle());
    }
    @Test
    @Order(3)
    void addedStock() {
        Stock stock = new Stock();
        stock.setTitle("New Stock");

        StockRepository stockRepository = Mockito.mock(StockRepository.class);
        StockServiceImpl stockServiceImpl = new StockServiceImpl(stockRepository);
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation->{
            Stock stockk = invocation.getArgument(0);
            stockk.setTitle("New Stock");

            return stockk;
        });

        Stock savedstock = stockServiceImpl.addStock(stock);

        verify(stockRepository, times(1)).save(any(Stock.class));

        assertNotNull(savedstock);

        assertEquals("New Stock", savedstock.getTitle());

    }




    @Test
    @Order(4)
    void retrieveStockById() {

        Long stockId = 1L;
        Stock stockRet = new Stock();
        stockRet.setIdStock(stockId);


        StockRepository stockRepository = Mockito.mock(StockRepository.class);
        StockServiceImpl stockServiceImpl = new StockServiceImpl(stockRepository);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stockRet));


        Stock retrievedStock1 = stockServiceImpl.retrieveStock(stockId);


        verify(stockRepository, times(1)).findById(stockId);


        assertNotNull(retrievedStock1);


        assertEquals("stock id", retrievedStock1.getTitle());

    }






}