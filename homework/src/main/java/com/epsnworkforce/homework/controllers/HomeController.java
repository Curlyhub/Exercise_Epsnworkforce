package com.epsnworkforce.homework.controllers;

import com.epsnworkforce.homework.models.Customer;
import com.epsnworkforce.homework.models.Transferency;
import com.epsnworkforce.homework.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
  @Autowired
  private CustomerService custumerService;

  @RequestMapping("/")
  public String home() {
    return "{'name':Hello World}";
  }

  @GetMapping("/custumer/list")
  public List<Customer> all() {
    return custumerService.getAllCustumer();
  }

  @PostMapping("/custumer/create")
  public ResponseEntity<Customer> createMember(@Valid @RequestBody Customer member) {
    var z = custumerService.saveCustumer(member);
    return ResponseEntity.ok(z);

  }

  @PutMapping("/custumer/update/{id}")
  public ResponseEntity<Customer> updateMember(@Valid @RequestBody Customer member,
      @PathVariable(value = "id") Long id) {
    return ResponseEntity.ok(custumerService.updateCustumer(member, id));
  }

  @DeleteMapping("/custumer/delete/{id}")
  public ResponseEntity<?> deleteMemeber(@PathVariable Long id) {
    Map<String, String> response = new HashMap<String, String>();
    if (custumerService.deleteCustumer(id)) {
      response.put("status", "success");
      response.put("message", "member deleted successfully");
      return ResponseEntity.ok(response);
    } else {
      response.put("status", "error");
      response.put("message", "Somthing went wrong when delete the member");
      return ResponseEntity.status(500).body(response);
    }
  }

  @PostMapping("/transfer/create")
  public ResponseEntity<Boolean> transfers(@RequestBody Transferency transfer) throws IOException {
    var z = custumerService.makeTransfer(transfer);
    return ResponseEntity.ok(z);

  }


  @GetMapping("/transfers/list")
  public List<Transferency>  transfers() {
   // var z = custumerService.(transfer);
    return null;
  }

}
