package com.epsnworkforce.homework.services;

import com.google.gson.Gson;
import com.epsnworkforce.homework.models.Convertor;
import com.epsnworkforce.homework.models.Customer;
import com.epsnworkforce.homework.models.Transferency;
import com.epsnworkforce.homework.repositories.CustomerRepository;
import com.epsnworkforce.homework.repositories.TransferencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;


@Service("CustomerService")
public class CustomerServiceImp implements CustomerService{

    private String convertion_url = "https://free.currconv.com/api/v7/convert?q=%s_%s&compact=ultra&apiKey=1740b9a2fff1e038b301";
    private static String rate = "rate";
    private static String underscore = "_";

    @Autowired
    private CustomerRepository memberRepository;

    @Autowired
    private TransferencyRepository transferRepository;



    private double getConversion(String currA, String currB) throws IOException {
        double res = -1.0;
        String uri = String.format(convertion_url, currA, currB);
        var restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        result =result.replace(currA + underscore+ currB,rate);
        Gson g = new Gson();
        var obj = g.fromJson(result, Convertor.class);
        res = obj.getRate();
        return res;
    }

    private boolean AtomicOperatin(Customer sender, Customer receiver, double quantity) throws IOException{
        var temporal_sender_balance = sender.getMoney();
        var temporal_reiceiver_balance = receiver.getMoney();
        if (sender.getCurrency().equals(receiver.getCurrency())) {
            receiver.setMoney(temporal_reiceiver_balance + quantity);
        } else {

            var rate = getConversion(sender.getCurrency(), receiver.getCurrency());
            receiver.setMoney(temporal_reiceiver_balance + rate * quantity);
        }
        sender.setMoney(temporal_sender_balance - quantity);
        var z = memberRepository.save(receiver);
        var w = memberRepository.save(sender);
        return (z.getCustomerId().equals(receiver.getCustomerId()) && w.getCustomerId().equals(sender.getCustomerId()));
    }

    public Customer findCustumer(Long id) {
        return memberRepository.getOne(id);
    }

    public List<Customer> getAllCustumer() {
        return (List<Customer>) memberRepository.findAll();
    }

    public Customer saveCustumer(Customer member) {
        var existingCustomer = memberRepository.findByName(member.getName());
        if (existingCustomer != null) {
            if (member.getMoney() > 0) {
                existingCustomer.setCurrency(member.getCurrency());
                existingCustomer.setMoney(member.getMoney());
                return memberRepository.save(existingCustomer);
            } else {
                existingCustomer.setCurrency(member.getCurrency());
                existingCustomer.setMoney(member.getMoney());
                return memberRepository.save(existingCustomer);
            }
        }
        var z = memberRepository.save(member);
        return z;
    }

    public Customer updateCustumer(Customer member, Long id) {
        Customer updateMember = memberRepository.findById(id).orElse(null);
        if (updateMember != null) {
            updateMember.setCurrency(member.getCurrency());
            if (!member.getTreasury()) {
                if (member.getMoney() > 0)
                    updateMember.setCurrency(member.getCurrency());
                updateMember.setMoney(member.getMoney());
                return memberRepository.save(updateMember);
            } else {
                updateMember.setCurrency(member.getCurrency());
                updateMember.setMoney(member.getMoney());
                return memberRepository.save(updateMember);
            }
        }
        return updateMember;
    }

    public Boolean deleteCustumer(Long id) {
        Customer delMember = memberRepository.findById(id).orElse(null);
        if (delMember != null) {
            memberRepository.delete(delMember);
            return true;
        }
        return false;
    }

    public Boolean makeTransfer(Transferency transfer) throws IOException {
        var sender = memberRepository.findById(transfer.getFromId()).orElse(null);
        var receiver = memberRepository.findById(transfer.getToId()).orElse(null);
        
        if ( sender.getCustomerId().equals(receiver.getCustomerId())){
            return false;
        }

        if ( sender.getMoney()< transfer.getQuantity()){
            return false;
        }

        if (sender.getTreasury()) {
            return AtomicOperatin(sender, receiver, transfer.getQuantity());
        } else {
            if (sender.getMoney() >= transfer.getQuantity()) {
                return AtomicOperatin(sender, receiver, transfer.getQuantity());
            }
        }
        return false;
    }


    public List<Transferency>  getAllTransferencies(){        
        return transferRepository.findAll();
    }

}