package bgu.spl181.net.impl.BBtpc;

import bgu.spl181.net.impl.UserService.UserConnections;
import bgu.spl181.net.interfaces.BidiMessagingProtocol;
import bgu.spl181.net.interfaces.Connections;
import bgu.spl181.net.interfaces.Server;
import bgu.spl181.net.interfaces.MessageEncoderDecoder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class BaseServer<T> implements Server<T> {

    private final int port;
    private final Supplier<BidiMessagingProtocol<T>> protocolFactory;
    private final Supplier<MessageEncoderDecoder<T>> encdecFactory;
    private ServerSocket sock;

    public BaseServer(
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encdecFactory) {
        this.port = port;
        this.protocolFactory = protocolFactory;
        this.encdecFactory = encdecFactory;
		this.sock = null;
    }

    @Override
    public void serve() {
        try (ServerSocket serverSock = new ServerSocket(port)) {
			System.out.println("Server started");
            Connections<T> clients = new UserConnections<T>();
            this.sock = serverSock; //just to be able to close
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSock = serverSock.accept();
                System.out.println("ACCEPTED!");
                BidiMessagingProtocol<T> protocol = protocolFactory.get();
                BlockingConnectionHandler<T> handler = new BlockingConnectionHandler<>(
                        clientSock,
                        encdecFactory.get(),
                        protocol);
                int id = clients.connect(handler);
                protocol.start(id, clients);
                execute(handler);
            }
        } catch (IOException ignore) {}
        System.out.println("MovieRental closed!!!");
    }

    @Override
    public void close() throws IOException {
		if (sock != null)
			sock.close();
    }

    protected abstract void execute(BlockingConnectionHandler<T> handler);

}
