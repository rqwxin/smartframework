package com.cgx.smart4j.modules.Customer.controller;

import com.cgx.smart4j.framework.Bean.Param;
import com.cgx.smart4j.framework.Bean.View;
import com.cgx.smart4j.framework.annotation.Action;
import com.cgx.smart4j.framework.annotation.Controller;
import com.cgx.smart4j.framework.annotation.Inject;
import com.cgx.smart4j.modules.Customer.dto.Customerdto;
import com.cgx.smart4j.modules.Customer.service.CustomerService;

import java.util.List;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-30 11:05
 **/
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View index(Param param){
        List<Customerdto> list = customerService.getList();
        return  new View("customer.jsp").addModel("list",list);
    }
}
