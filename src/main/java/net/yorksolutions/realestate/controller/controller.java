package net.yorksolutions.realestate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.yorksolutions.realestate.model.RealEstate;
import net.yorksolutions.realestate.repository.RealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@RequestMapping("/realestate")
@RestController
public class controller {
    @Autowired
    RealEstateRepository realEstateRepository;


    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/all")
    String getAllRealEstate() throws JsonProcessingException {
        return objectMapper.writeValueAsString(realEstateRepository.findAll());
    }

    @GetMapping("/getbyRowAmount")
    String getRealEstateByRows(@RequestParam("rows") String rows) throws JsonProcessingException {
        List<RealEstate> realEstateList = (List<RealEstate>) realEstateRepository.findAll();
        realEstateList = realEstateList.stream().limit(Long.parseLong(rows)).collect(Collectors.toList());
        return objectMapper.writeValueAsString(realEstateList);
    }

    @PostMapping("/add")
    String putRealestate(@RequestBody String body) {
        try {
            RealEstate realEstate = objectMapper.readValue(body, RealEstate.class);
            realEstateRepository.save(realEstate);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
        return "success";
    }

    @GetMapping("/search")
    String searchRealEstate(@RequestParam("fname") String fname) throws JsonProcessingException {
        List<RealEstate> realEstateList = (List<RealEstate>) realEstateRepository.findAll();
        var Result = new ArrayList<RealEstate>();
        for (RealEstate realEstate : realEstateList) {
            if (Objects.equals(realEstate.getFname(), "Madison")) {
                Result.add(realEstate);


            }
        } return objectMapper.writeValueAsString(Result);
    }

//    Result@GetMapping("/price")
//    String searchRealEstateByPrice
}