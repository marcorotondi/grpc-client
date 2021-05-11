package com.marco.grpcclient.runner;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.marco.Echo;
import io.grpc.marco.GreeterGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CommandRunner.class);

    @Override
    public void run(String... args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 6565).usePlaintext()
                .keepAliveWithoutCalls(true).build();
        var greeterStub = GreeterGrpc.newBlockingStub(channel);
        for (int i = 0; i < 10000; i++) {
            Echo.HelloReply helloReply = greeterStub.sayHello(Echo.HelloRequest.newBuilder()
                    .setName("Marco")
                    .build());
            log.info("Response from server: {} {}", i, helloReply.getMessage());
        }
    }
}
