package nl.whitehorses.service;

import com.googlecode.jsonrpc4j.JsonRpcMethod;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;

@AutoJsonRpcServiceImpl
public class HelloWorldImpl implements HelloWorld {


    @Override
    @JsonRpcMethod("sayHello")
    public String sayHello(@JsonRpcParam("inputString") String inputString) {
        return "Hello " + inputString;
    }
}
