package edu.eci.cvds.stockManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.cvds.stockManager.model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockManagerService {

    private List<Product> products = new ArrayList<>();
    private List<Subscriber> subscribers = new ArrayList<>();

    @Autowired
    private Suscriber suscriber;

    public Boolean addProduct(Product product){
        Product busqueda;
        for (Product p:products){
            if p.equals(product){
                busqueda = p;
            }
        }
        if (busqueda == null) {
            products.add(product);
            return true
        }else{
            return false
        }
    }

    public Boolean modifyStock(String idProduct, Integer newQuant){
        if(!products.stream().anyMatch(p->p.getId().equals(idProduct)) || newQuant<0) return false;
        else{
            Product product = this.products.stream().filter(p-> p.getId().equals(idProduct)).findFirst().orElse(new Product());
            product.setQuantity(newQuant);
            for(Subscriber sub: subscribers){
                sub.notifyChange(product.getName(),newQuant);
            }
        }
        return true;
    }
    public List<Subscriber> getSubscribers(){
        return this.subscribers;
    }
}