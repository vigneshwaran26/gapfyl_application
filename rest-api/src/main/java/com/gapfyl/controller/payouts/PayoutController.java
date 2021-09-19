package com.gapfyl.controller.payouts;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.payouts.BankAccountRequest;
import com.gapfyl.dto.payouts.VPAAccountRequest;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.services.payouts.PayoutAccountService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

@RestController
@RequestMapping("/api/1.0/payouts")
public class PayoutController extends AbstractController {

    @Autowired
    PayoutAccountService payoutAccountService;

    @PostMapping("/create/bank-account")
    ResponseEntity addBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
        return ResponseEntity.ok().body(payoutAccountService.addBankAccount(bankAccountRequest, getCurrentUser()));
    }

    @PutMapping("/update/bank-account/{payoutAccountId}/{bankAccountId}")
    ResponseEntity updateBankAccount(@PathVariable("payoutAccountId") String payoutAccountId,
                                     @PathVariable("bankAccountId") String bankAccountId,
                                     @RequestBody @Valid BankAccountRequest bankAccountRequest) {

        payoutAccountService.updateBankAccount(payoutAccountId, bankAccountId, bankAccountRequest, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @DeleteMapping("/delete/bank-account/{payoutAccountId}/{bankAccountId}")
    ResponseEntity deleteBankAccount(@PathVariable("payoutAccountId") String payoutAccountId,
                                     @PathVariable("bankAccountId") String bankAccountId) {
        payoutAccountService.deleteBankAccount(payoutAccountId, bankAccountId, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PostMapping("/create/vpa-account")
    ResponseEntity addVPAAccount(@RequestBody VPAAccountRequest vpaAccountRequest) {
        return ResponseEntity.ok().body(payoutAccountService.addVPAAccount(vpaAccountRequest, getCurrentUser()));
    }

    @PutMapping("/update/vpa-account/{payoutAccountId}/{vpaAccountId}")
    ResponseEntity updateVpaAccount(@PathVariable("payoutAccountId") String payoutAccountId,
                                    @PathVariable("vpaAccountId") String vpaAccountId,
                                    @RequestBody @Valid VPAAccountRequest vpaAccountRequest) {

        payoutAccountService.updateVPAAccount(payoutAccountId, vpaAccountId, vpaAccountRequest, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @DeleteMapping("/delete/vpa-account/{payoutAccountId}/{vpaAccountId}")
    ResponseEntity deleteVpaAccount(@PathVariable("payoutAccountId") String payoutAccountId,
                                    @PathVariable("vpaAccountId") String vpaAccountId) {
        payoutAccountService.deleteVPAAccount(payoutAccountId, vpaAccountId, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PutMapping("/update/payout-account/{payoutAccountId}")
    ResponseEntity setPayoutAccount(@PathVariable("payoutAccountId") String payoutAccountId,
                                    @RequestParam("accountType") AccountType accountType,
                                    @RequestParam("accountId") String accountId) {
        payoutAccountService.setPayoutAccount(payoutAccountId, accountType, accountId, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @GetMapping("/fetch/payout-account")
    ResponseEntity userPayoutAccount() {
        return ResponseEntity.ok().body(payoutAccountService.fetchPayoutAccountResponse(getCurrentUser()));
    }
}
