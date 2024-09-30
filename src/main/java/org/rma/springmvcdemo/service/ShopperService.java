package org.rma.springmvcdemo.service;

import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopperService {

    @Autowired
    private ShopperRepository shopperRepository;

    public Optional<Shopper> findByUsername(String username) {
        return shopperRepository.findByUsername(username);
    }

    public Shopper createShopper(Shopper shopper) {
        return shopperRepository.save(shopper);
    }

    public Shopper getShopperById(Long id) {
        return shopperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopper not found"));
    }

    public void deleteShopper(Long id) {
        shopperRepository.deleteById(id);
    }

    public Shopper updateShopper(Long id, Shopper updatedShopper) {
        return shopperRepository.findById(id)
                .map(shopper -> {
                    shopper.setUsername(updatedShopper.getUsername());
                    shopper.setPassword(updatedShopper.getPassword());

                    return shopperRepository.save(shopper);
                })
                .orElseThrow(() -> new RuntimeException("Shopper not found"));
    }
}