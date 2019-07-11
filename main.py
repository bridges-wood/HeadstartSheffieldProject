import socket
import sys

HOST, PORT = "myranker.co.uk", 7000
data = '{leagueTablePref:11,studentSatisfactionPref:11,employabilityPref:11,researchQualityPref:11,studentToStaffPref:2,costOfLivingPref:1,internationalStudentPref:11}'

# data =

# Create a socket (SOCK_STREAM means a TCP socket)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

print 1

# try:
# Connect to server and send data
sock.connect((HOST, PORT))
sock.sendall(data + '\n')

print 2

# Receive data from the server and shut down
received = sock.recv(1024)

print 3

# finally:
sock.close()

print (data)
print ("Received: {}".format(received))
