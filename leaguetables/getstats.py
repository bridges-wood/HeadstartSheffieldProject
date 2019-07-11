import requests
import codecs
import re


def getstats():
# Gets info from the site
	r = requests.get('https://www.theguardian.com/education/ng-interactive/2019/jun/07/university-guide-2020-league-table-for-computer-science-information')
	# p = re.compile('<table ?.*?>', re.DOTALL)
	m = re.search('<tbody>(.*?)<\/tbody>', r.text, re.DOTALL)
	guardianTable = m.group(1)
	f = codecs.open("guardian-table.txt", "w+", "utf-8")
	f.write(guardianTable)
	f.close()

	m = re.search('<tr ?.*?>(.*?)<\/tr>', guardianTable, re.DOTALL)
	# for i in range(len(m)):
	guardianRow = m.group(1)
	f = codecs.open("guardian-row.txt", "w+", "utf-8")
	f.write(guardianRow)
	f.close()

def main():
	getstats()
	#getstats('https://www.thecompleteuniversityguide.co.uk/league-tables/rankings?s=computer%20science')

main()



