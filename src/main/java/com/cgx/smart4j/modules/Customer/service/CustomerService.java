package com.cgx.smart4j.modules.Customer.service;

import com.cgx.smart4j.framework.annotation.Service;
import com.cgx.smart4j.modules.Customer.dto.Customerdto;

import java.util.LinkedList;
import java.util.List;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-30 11:07
 **/
@Service
public class CustomerService {

    public List<Customerdto> getList(){
        List<Customerdto> ls = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            Customerdto c1 = new Customerdto();
            c1.setId((long) i);
            c1.setAccount("customer"+i);
            c1.setName("消费者"+i);
            String sex = "男";
            if (i%2==0){
                sex = "女";
            }
            c1.setSex(sex);
            ls.add(c1);
        }
        return  ls;
    }
}
