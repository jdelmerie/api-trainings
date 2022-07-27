package fr.fms.apitrainings.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double total;

    private int number;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Customer customer;

    public Orders(Long id, Date date, double total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                '}';
    }
}
