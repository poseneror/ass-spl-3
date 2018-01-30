package bgu.spl181.net.impl.BBreactor;

import bgu.spl181.net.impl.UserService.StringEncoderDecoder;
import bgu.spl181.net.impl.MovieRental.MovieRentalProtocol;
import bgu.spl181.net.impl.UserService.SharedProtocolData;
import bgu.spl181.net.interfaces.Server;

/**
 * Created by Or on 01/01/2018.
 */
public class ReactorMain {
    public static void main(String[] args) {
        SharedProtocolData sharedProtocolData = new SharedProtocolData();
        Server.reactor(
                8,
                Integer.parseInt(args[0]),
                () -> new MovieRentalProtocol(sharedProtocolData),
                () -> new StringEncoderDecoder()
        ).serve();
    }
}
