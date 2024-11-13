package com.example.javaassignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SalesDatabase implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Sale> sales;

    public SalesDatabase() {
        sales = new ArrayList<>();
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public List<Sale> getSales() {
        return sales;
    }
    public void deleteSalesByMovie(String movieTitle) {
        Iterator<Sale> iterator = sales.iterator();
        while (iterator.hasNext()) {
            Sale sale = iterator.next();
            if (sale.getMovieTitle().equals(movieTitle)) {
                iterator.remove();
            }
        }
    }
}
