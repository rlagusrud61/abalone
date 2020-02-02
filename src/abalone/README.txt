Installation:
download these packages: abalone, client, exceptions, protocol, server, tests
now check that all the code compiles and that all the files are in the right packages.

for testing:
just run each test you want to see and the IDE will give an overview of the test.

for playing abalone:
first start the server,
on the server console, fill in a port number, for example: 8888.

now start up the client on the same or a different laptop.
fill in the right IP and the same port number on which you started the server.
the client should connect and ask you for your name and the amount of players, fill this in to your needs.
if game queue is full, the game will start and asks for a move from one of the client, now fill in:
m;(the direction you want to move);(the marbles indexes you want to move)
an example would be:
m;3;13
m;0;45,46,47