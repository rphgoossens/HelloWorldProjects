package nl.whitehorses.service;

import com.googlecode.jsonrpc4j.JsonRpcService;
import org.springframework.stereotype.Service;

@Service
@JsonRpcService("/rpc/hello")
public interface HelloWorld {

    String sayHello(String inputString);
}
