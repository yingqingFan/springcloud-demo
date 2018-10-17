package com.yingqing.controller;

import com.yingqing.entity.Account;
import com.yingqing.mq.springcloudstream.MqProducer;
import com.yingqing.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts")
@Api(tags = {"测试"})
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    MqProducer mqProducer;


    @ApiOperation(value = "save account", notes = "save")
    @ApiImplicitParam(value = "account body",paramType = "body", name = "account", dataType = "Account", required = true)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Account> save(@RequestBody Account account){
//        mqProducer.send("hello");
        account = accountService.save(account);
        return new ResponseEntity<Account>(account, HttpStatus.CREATED);
    }

    @ApiOperation(value = "find by id", notes = "find")
    @ApiImplicitParam(value = "id",paramType = "path", name = "id", dataType = "long", required = true)
    @RequestMapping(value = "/{id}",  method = RequestMethod.GET)
    public ResponseEntity<Account> findById(@PathVariable Long id){
        Account account = accountService.findById(id);
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }
}
