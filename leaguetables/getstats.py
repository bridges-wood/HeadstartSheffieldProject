import requests
import codecs
import re
import csv
import random


def getrussellgroups():
	r = requests.get('https://russellgroup.ac.uk/about/our-universities/')
	m = re.search('<ul>(.*?)<\/ul>', r.text, re.DOTALL)
	russellgrouplist = m.group()

	with open('russellgroupunis.csv','w+') as myfile:
		wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)

		russellgroupunis = re.findall('<a .*?>(.*?)</a>', russellgrouplist, re.DOTALL)
		
		wr.writerow(russellgroupunis)
	return russellgroupunis

def getresearchstats():
	r = requests.get('https://www.university-list.net/uk/rank/univ-8089.htm')
	m = re.search('<tbody>(.*?)<\/tbody>', r.text, re.DOTALL)

	researchTable = m.group()
	# f = codecs.open("researchtable.txt", "w+", "utf-8")
	# f.write(researchTable)
	# f.close()

	# extracts all rows of the table
	researchRows = re.findall('<tr ?.*?>(.*?)<\/tr>', researchTable, re.DOTALL)
	#print(len(researchRows)) = 129
	researchOutput = []
	uni = []
	researchDatalist = []
	for i in range(len(researchRows)):
		#m.group(0) for cambridge would be 1 (its ranking)
		researchRow = researchRows[i]
		# f = codecs.open("research-rows-"+str(i)+".txt", "w+", "utf-8")
		# f.write(researchRow+'\n')
		# f.close()

		researchData = re.findall('<td ?.*?>(.*?)<\/td>', researchRows[i], re.DOTALL)
		# print(len(researchData))
		# print(type(researchData))
		for i in range(2):
			del researchData[0]
		for i in range(2):
			del researchData[1]
		for i in range(3):
			del researchData[2]

		for item in range(len(researchData)):
			if researchData[item] == 'n/a':
				researchData[item] = '0'

		researchDatalist.append(researchData)
			
	return researchDatalist

def getguardianstats():
# Gets info from the guardian
	r = requests.get('https://www.theguardian.com/education/ng-interactive/2019/jun/07/university-guide-2020-league-table-for-computer-science-information')
	m = re.search('<tbody>(.*?)<\/tbody>', r.text, re.DOTALL)
	# writes the 2nd table because there's another above it
	guardianTable = m.group(1)

	# extracts all rows of the table
	guardianRows = re.findall('<tr ?.*?>(.*?)<\/tr>', guardianTable, re.DOTALL)

	with open('guardiandata.csv', 'w+') as myfile:
		wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)

		for i in range(len(guardianRows)):
			
			# ignore odd entries (list of courses from unis)
			
			guardianData = re.findall('<td ?.*?>(.*?)<\/td>', guardianRows[i], re.DOTALL)

			if len(guardianData) != 12:
				continue
			# should return all tds
			# f = codecs.open("guardian-data-"+str(i)+".txt", "w+", "utf-8")
			# # guardianData will be a list in the format ['item', 'item', 'item']
			# f.write(str(guardianData))
			# f.close()

			# given the uni name is an <a> tag, returns the uni name
			m = re.findall('<a class=\"js-institution-link c-table__institution-link\".*?>(.*?)</a>', str(guardianData), re.DOTALL)
			uni = str(m).strip("['']")
			#try:
			#print(guardianData)
			guardianData[1] = uni
			# gets russell group status
			

			ranking = guardianData[0]
			del guardianData[2]

			# replaces all n/a entries with 0
			for item in range(len(guardianData)):
				if guardianData[item] == 'n/a':
					guardianData[item] = '0'

			if type(guardianData[2]) == int and type(guardianData[3]) == int and type(guardianData[4]) == int:
				studentsat = (guardianData[2] + guardianData[3] + guardianData[4]) / 3
				russellgroupunis = getrussellgroups()
				if uni in russellgroupunis:
					guardianData[2] = True
				else:
					guardianData[2] = False
			else:
				studentsat = 0

			russellgroupunis = getrussellgroups()
			if uni in russellgroupunis:
				guardianData[2] = True
			else:
				guardianData[2] = False

			guardianData[3] = studentsat
			guardianData[0] = uni
			guardianData[1] = "city" #sets all to be city unis
			guardianData[4] = ranking
			studentratio = guardianData[5]
			guardianData[5] = ranking
			guardianData[6] = 0
			entrytariff = guardianData[7]
			guardianData[7] = studentratio
			researchDatalist = getresearchstats()

			for j in range(len(researchDatalist)):
				researchoutput = researchDatalist[j]
				if researchoutput[0] == uni:
					guardianData[8] = researchoutput[1]

			gradprospects = guardianData[9]
			guardianData[9] = entrytariff
			guardianData[10] = 0
			guardianData.append(0)
			guardianData.append(gradprospects)

			
			# except:
			# 	print(guardianData)

			# write the list to a csv file
			wr.writerow(guardianData)







		#print(researchRow) is in format <td>2nd</td><td title="2nd in 2017">0</td><td>University of Oxford</td><td>570</td><td>4.13</td><td>3.34</td><td>83.7</td><td>998</td><td>10.3</td>
	# print(researchRows) a list

def main():
	getguardianstats()
	#getstats('https://www.thecompleteuniversityguide.co.uk/league-tables/rankings?s=computer%20science')

main()



