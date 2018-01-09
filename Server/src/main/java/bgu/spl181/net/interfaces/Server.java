package bgu.spl181.net.interfaces;

import bgu.spl181.net.impl.BBtpc.BaseServer;
import bgu.spl181.net.impl.BBtpc.BlockingConnectionHandler;
import bgu.spl181.net.impl.BBreactor.Reactor;

import java.io.Closeable;
import java.util.function.Supplier;

public interface Server<T> extends Closeable {

    /**
     * The main loop of the MovieRental, Starts listening and handling new clients.
     */
    void serve();

    /**
     *This function returns a new instance of a thread per client pattern MovieRental
     * @param port The port for the MovieRental socket
     * @param protocolFactory A factory that creats new MessagingProtocols
     * @param encoderDecoderFactory A factory that creats new MessageEncoderDecoder
     * @param <T> The Message Object for the protocol
     * @return A new Thread per client MovieRental
     */
    public static <T> Server<T> threadPerClient(
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory) {
        return new BaseServer<T>(port, protocolFactory, encoderDecoderFactory) {
            @Override
            protected void execute(BlockingConnectionHandler<T> handler) {
                new Thread(handler).start();
            }
        };

    }

    /**
     * This function returns a new instance of a reactor pattern MovieRental
     * @param nthreads Number of threads available for protocol processing
     * @param port The port for the MovieRental socket
     * @param protocolFactory A factory that creats new MessagingProtocols
     * @param encoderDecoderFactory A factory that creats new MessageEncoderDecoder
     * @param <T> The Message Object for the protocol
     * @return A new reactor MovieRental
     */
    public static <T> Server<T> reactor(
            int nthreads,
            int port,
            Supplier<BidiMessagingProtocol<T>> protocolFactory,
            Supplier<MessageEncoderDecoder<T>> encoderDecoderFactory) {
        return new Reactor<T>(nthreads, port, protocolFactory, encoderDecoderFactory);
    }

}
