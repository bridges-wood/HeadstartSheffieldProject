import socket
import sys

HOST, PORT = "localhost", 8000
data = '{leagueTablePref:3,studentSatisfactionPref:6,employabilityPref:4,researchQualityPref:7,studentToStaffPref:4,costOfLivingPref:6,internationalStudentPref:3}'

# data = 

# Create a socket (SOCK_STREAM means a TCP socket)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# try:
# Connect to server and send data
sock.connect((HOST, PORT))
sock.sendall(data + '\n')

# Receive data from the server and shut down
received = sock.recv(1024)
# finally:
sock.close()

print (data)
print ("Received: {}".format(received))
