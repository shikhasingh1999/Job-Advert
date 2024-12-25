package com.example.common.entity;

import com.example.common.enums.AdvertStatus;
import com.example.common.enums.Advertiser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "adverts")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advert extends BaseEntity {

    private String userId;
    private String name;
    private String description;
    private int deliveryTime;
    private int price;
    private String imageId;

    @Enumerated(EnumType.STRING)
    private AdvertStatus status;

    @Enumerated(EnumType.STRING)
    private Advertiser advertiser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job-id")
    private Job job;

    // FetchType.LAZY will first load the main entity (Owner), then the related entity (Blog) on demand.

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Offer> offers;

//    Spring Boot can gather the necessary information about the
//    database from the connection URL, so it is not necessary to
//    specify datasource.driver-class-name. However, we will be safer if we
//    specify the driver-class-name.
//
//    When the application is running, jpa.show-sql displays Hibernate
//    SQL queries in the console, jpa.hibernate.ddl-auto is set to update,
//    which updates the database schema every time we restart the application,
//    and hibernate.dialect indicates which database dialect we are using.

//    But you have two options for how linked child table students should be loaded:
//    To load it together with the rest of the fields (i.e. eagerly), or
//    To load it on-demand (i.e. lazily) when you call the university's getStudents() method.

//    When a university has many students it is not efficient to load all of its students
//    together with it, especially when they are not needed and in suchlike
//    cases you can declare that you want students to be loaded when they are
//    actually needed. This is called lazy loading.

//    LAZY = fetch when needed
//    EAGER = fetch immediately

//    For optional = false

//    If the association is optional, Hibernate has no way of knowing if
//    an address exists for a given person without issuing a query.
//    So it can't populate the address field with a proxy, because there
//    could be no address referencing the person, and it can't populate it
//    with null, because there might be an address referencing the person.
//
//    When you make the association mandatory (i.e. optional=false),
//    it trusts you and assumes that an address exists, since the association
//    is mandatory. So it directly populates the address field with a proxy,
//    knowing that there is an address referencing the person.

//    joincolumn & mappedby

//    The annotation @JoinColumn indicates that this entity is the
//    owner of the relationship (that is: the corresponding table has a
//    column with a foreign key to the referenced table), whereas the
//    attribute mappedBy indicates that the entity in this side is the
//    inverse of the relationship, and the owner resides in the "other"
//    entity. This also means that you can access the other table from the
//    class which you've annotated with "mappedBy" (fully bidirectional relationship).

//    CascadeType.ALLare all operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH)
//    that are propagated to the associated entity to the relating entities
//    in persistence context.

}
