#include <stdlib.h>
#include <connectionHandler.h>
#include <boost/thread.hpp>

std::atomic<bool> active(true);

class ServerListener{
private:
    ConnectionHandler *_connectionHandler;
public:
    ServerListener(ConnectionHandler *connectionHandler):
            _connectionHandler(connectionHandler){}
    void run() {
       while (active) {
            std::string answer;
            if (!_connectionHandler -> getLine(answer)) {
                break;
            }
            int len = answer.length();
            answer.resize(len - 1);
            std::cout << answer << std::endl;
           if(answer == "ACK signout succeeded"){
               std::cout << "Press enter to exit..." << std::endl;
               active = false;
               break;
           }
        }
    }
};

class KeyboardListener{
private:
    ConnectionHandler *_connectionHandler;
public:
    KeyboardListener(ConnectionHandler *connectionHandler):
            _connectionHandler(connectionHandler){}

    void run(){
        while (active) {
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            if (!_connectionHandler -> sendLine(line)) {
                break;
            }
        }
    }
};

int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    ServerListener serverListener(&connectionHandler);
    boost::thread serverThread(&ServerListener::run, &serverListener);

    KeyboardListener keyboardListener(&connectionHandler);
    boost::thread keyboardThread(&KeyboardListener::run, &keyboardListener);

    keyboardThread.join();
    serverThread.join();
    return 0;
}
