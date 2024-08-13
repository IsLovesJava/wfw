package cn.pkx.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceNormalB {
    @Autowired
    ServiceNormalA serviceNormalA;
}
