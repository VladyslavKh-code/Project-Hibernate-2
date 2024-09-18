package org.example;

import org.configuration.PrepareConfiguration;
import project.movie.*;

import project.movie.*;
import project.movie.dao.*;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PrepareConfiguration configuration = new PrepareConfiguration();
        Customer customer = configuration.addNewCustomer();
        configuration.customerReturnFilm();
        configuration.customerBoughtInventory(customer);
        configuration.newFilmAvailable();

    }
}

