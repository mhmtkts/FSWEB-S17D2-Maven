package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.DeveloperTax;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private Taxable taxable;


    public Map<Integer, Developer> developers = new HashMap<>();

    @PostConstruct
    public void init(){
        developers.put(1,new Developer(1,"Mahmut",40000, Experience.JUNIOR));
        developers.put(2,new Developer(2,"Zeynep",60000, Experience.MID));
        developers.put(3,new Developer(3,"Mehmet",80000, Experience.SENIOR));
    }


    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;

    }
    @GetMapping
    public List<Developer> findAll(){
        return developers.values().stream().toList();
    }


    @GetMapping("/{id}")
    public Developer findById(@PathVariable int id){
        return developers.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Developer save(@RequestBody Developer developer) {
        developers.put(developer.getId(), developer);
        if (developer.getExperience() == Experience.JUNIOR) {
            developer.setSalary(taxable.getSimpleTaxRate());
        } else if (developer.getExperience() == Experience.MID) {
            developer.setSalary(taxable.getMiddleTaxRate());
        } else {
            developer.setSalary(taxable.getUpperTaxRate());
        }
        return developers.get(developer.getId());
    }


    @PutMapping("/{id}")
    public Developer save(@PathVariable int id, @RequestBody Developer developer){
        developers.put(id, developer);
        return developers.get(id);
    }

    @DeleteMapping("/{id}")
    public Developer save(@PathVariable int id){
        return developers.remove(id);
    }


}
