CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_thread -lboost_system

all: BBclient
	g++ -o bin/BBclient bin/connectionHandler.o bin/MovieRentalClient.o $(LDFLAGS)

BBclient: bin/connectionHandler.o bin/MovieRentalClient.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/MovieRentalClient.o: src/MovieRentalClient.cpp
	g++ $(CFLAGS) -o bin/MovieRentalClient.o src/MovieRentalClient.cpp
	
.PHONY: clean
clean:
	rm -f bin/*
