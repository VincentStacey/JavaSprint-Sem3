package src.main.java.models;

import java.util.ArrayList;
import java.util.List;

public class Buyer extends User {
    private List<Integer> purchasedProductIds; 

    public Buyer() {
        this.setRole("buyer");
        this.purchasedProductIds = new ArrayList<>();
    }

    public void addPurchasedProduct(int productId) {
        purchasedProductIds.add(productId);
    }

    public List<Integer> getPurchasedProductIds() {
        return purchasedProductIds;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", purchasedProducts=" + purchasedProductIds +
                '}';
    }
}


