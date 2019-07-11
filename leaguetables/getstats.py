import requests
import codecs
import re
import csv


def getstats():
# Gets info from the site
	r = requests.get('https://www.theguardian.com/education/ng-interactive/2019/jun/07/university-guide-2020-league-table-for-computer-science-information')
	m = re.search('<tbody>(.*?)<\/tbody>', r.text, re.DOTALL)
	# writes the 2nd table because there's another above it
	guardianTable = m.group(1)
	f = codecs.open("guardian-table.txt", "w+", "utf-8")
	f.write(guardianTable)
	f.close()

	# extracts all rows of the table
	guardianRows = re.findall('<tr ?.*?>(.*?)<\/tr>', guardianTable, re.DOTALL)
	for i in range(len(guardianRows)):
		#m.group(0) for cambridge would be 1 (its ranking)
		guardianRow = guardianRows[i]
		f = codecs.open("guardian-row-"+str(i)+".txt", "w+", "utf-8")
		f.write(guardianRow+'\n')
		f.close()

	with open('guardiandata.csv', 'w+') as myfile:
		wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)

		for i in range(len(guardianRows)):
			
			# ignore odd entries (list of courses from unis)
			
			guardianData = re.findall('<td ?.*?>(.*?)<\/td>', guardianRows[i], re.DOTALL)
			print(len(guardianData))

			if len(guardianData) != 12:
				continue
			# should return all tds
			f = codecs.open("guardian-data-"+str(i)+".txt", "w+", "utf-8")
			# guardianData will be a list in the format ['item', 'item', 'item']
			f.write(str(guardianData))
			f.close()

			# given the uni name is an <a> tag, returns the uni name
			m = re.findall('<a class=\"js-institution-link c-table__institution-link\".*?>(.*?)</a>', str(guardianData), re.DOTALL)
			uni = str(m).strip("['']")
			#try:
			#print(guardianData)
			guardianData[1] = uni
			# gets rid of some useless td entry
			del guardianData[2]
			# replaces all n/a entries with 0
			for item in range(len(guardianData)):
				if guardianData[item] == 'n/a':
					guardianData[item] = '0'
			# except:
			# 	print(guardianData)

			# write the list to a csv file
			wr.writerow(guardianData)




def main():
	getstats()
	#getstats('https://www.thecompleteuniversityguide.co.uk/league-tables/rankings?s=computer%20science')

main()



