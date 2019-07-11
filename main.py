import socket
import sys

HOST, PORT = "localhost", 8000
data = '{leagueTablePref:5,studentSatisfactionPref:5,employabilityPref:5,researchQualityPref:5,studentToStaffPref:5,costOfLivingPref:5,internationalStudentPref:5}'

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
