package project.movie;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store", schema = "movie")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Byte storeId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address storeAddress;

    @OneToOne
    @JoinColumn(name = "manager_staff_id")
    private Staff storeStaff;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public Byte getStoreId() {
        return storeId;
    }

    public void setStoreId(Byte storeId) {
        this.storeId = storeId;
    }

    public Address getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(Address storeAddress) {
        this.storeAddress = storeAddress;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Staff getStoreStaff() {
        return storeStaff;
    }

    public void setStoreStaff(Staff storeStaff) {
        this.storeStaff = storeStaff;
    }
}
