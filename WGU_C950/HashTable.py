from Package import Package
import csv
class HashTable:
    '''
    Hash table that stores all package data.
    Implements direct hashing using the package ID as the key.
    '''

    def  __init__(self, capacity=100):
        '''
        Initializes a HashTable with buckets equal to the capacity (default = 100).
        Loads data into table from csv files and initializes variables.
        '''
        # Initializes package & address table
        self.package_table = []
        self.num_addresses = 0
        self.graph = []

        # Creates blank bucket_lists for each bucket in the table
        for bucket in range(capacity):
            self.package_table.append(None)

        # Loads data from csv into tables & sets num_addresses
        self.table_from_csv()
        self.graph_from_csv()
        self.address_count()

    def __repr__(self):
        '''
        Returns a string of a table of the package's attributes.  

        Space-time complexity = O(N)
        '''
        idstring = "ID"
        addressstring = "ADDRESS"
        citystring = "CITY"
        zipstring = "ZIP"
        deadlinestring = "DEADLINE"
        statusstring = "STATUS"
        instructionstring = "INSTRUCTIONS"
         
        return_string = '---------------------------------------------------------------------------------------------------------------------------------------------------------------\n'
        return_string += f'{idstring:^10}|{addressstring:^42}|{citystring:^20}|{zipstring:^10}|{deadlinestring:^10}|{statusstring:^35}|{instructionstring:^20}\n'
        return_string += '---------------------------------------------------------------------------------------------------------------------------------------------------------------\n'
        for p in self.package_table:
            if type(p) == Package:
                return_string += f'{p.package_id:^10}|{p.address:^42}|{p.city:^20}|{p.zip_code:^10}|{p.deadline:^10}|{p.status:^35}| {p.instructions:^20}\n'
        return_string += '---------------------------------------------------------------------------------------------------------------------------------------------------------------\n'
        return return_string

    def address_count(self):
        '''
        Updates the number of addresses found in package_table

        Space-time complexity =  O(N)
        '''

        # Creates a list for holding unique address_id's
        # Initializes it with hub ID since hub isn't in package_table
        addresses = [0]

        # For each bucket, if it contains a package,
        #  and that package has an address_id not found in addresses, 
        #  add address_id to addresses
        for p in self.package_table:
            if type(p) == Package and p.address_id not in addresses:
                addresses.append(p.address_id)
        
        # Sets num_addresses to the length of addresses
        self.num_addresses = len(addresses)

    def insert_package(self, package):
        '''
        Takes package as argument, hashes it based on id, and inserts the package into table.

        Space-time complexity = O(1)
        '''
        # Hashes based on ID and inserts package into table
        bucket = package.package_id % len(self.package_table)
        self.package_table[bucket] = package

        # Updates num_addresses
        self.address_count()

    def retrieve(self, package_id):
        '''
        Retrieves package by ID.  

        Space-time complexity = O(1)
        '''

        # Retrieves bucket based on ID
        bucket = package_id % len(self.package_table)
        
        # If package found in bucket, set that bucket to none and return the package
        if type(self.package_table[bucket]) != None:
            package = self.package_table[bucket]
            return package

    def init1(self):
        '''
        Loads truck 1 based on package ID.
        This truck primarily holds the delayed packages
        and other nearby packages.

        Space-time complexity = O(N)
        '''

        # List of package ID's
        truck_1 = [1,2,4,6,7,8,10,17,22,24,25,26,28,29,32,33,40]


        # Initializes list to store packages
        packages = []
        
        # Loops through ID list and appends package to package list
        for p_id in truck_1:
            packages.append(self.retrieve(p_id))
        
        # Returns package list
        return packages

    def init2(self):
        '''
        Loads truck 2 based on package ID.
        This truck primarily holds packages restricted to this truck,
        urgent packages and nearby packages. 

        Space-time complexity = O(N)
        '''
        # List of package ID's
        truck_2 = [3,13,14,15,16,18,19,20,30,34,36,37,38,39]

        # Initializes list to store packages
        packages = []
        
        # Loops through ID list and appends package to package list
        for p_id in truck_2:
            packages.append(self.retrieve(p_id))
        
        # Returns package list
        return packages

    def init3(self):
        '''
        Loads truck 3 based on package ID.
        This truck holds packages not found in other trucks.
        All of these packages go EOD and will be delivered last.  

        Space-time complexity = O(N)
        '''

        # List of package ID's
        truck_3 = [5,9,11,12,21,23,27,31,35]
        
        # Initializes list to store packages
        packages = []

        # Loops through ID list and appends package to package list
        for p_id in truck_3:
            packages.append(self.retrieve(p_id))
        
        # Returns package list
        return packages

    def table_from_csv(self, file_name='CSV_Data\packages.csv'):
        '''
        Loads data from csv file into package_table.
        Creates package based on CSV data and inserts that package into hash table

        Space-time complexity = O(N)
        '''

        # Opens & reads from csv file
        with open(file_name) as csv_file:
            reader = csv.reader(csv_file, delimiter=',')
            
            # Loop through each row in file, create package based on it's information
            for row in reader:

                # Retrieves & sets package attributes
                package = Package(int(row[0]))
                package.address_id = row[1]
                package.address = row[2]
                package.city = row[3]
                package.state = row[4]
                package.zip_code = row[5]
                package.deadline = row[6]
                package.weight = row[7]
                package.instructions = row[8]

                # Inserts package
                self.insert_package(package)

    def graph_from_csv(self, file_name='CSV_Data\distances-filled.csv'):
        '''
        Loads data from csv file into graph.

        Space-time complexity = O(N^2)
        '''
        
        # opens & reads from csv file 
        with open(file_name) as csv_file:
            reader = csv.reader(csv_file, delimiter=',')

            # Loop through each row in file
            for row in reader:
                current_row = []
                
                # Ignores the first column in csv file, as site name will not be used in the project
                for c_index, column in enumerate(row): 
                    if c_index > 1:
                        current_row.append(column)
                
                # Appends current row to distance graph
                self.graph.append(current_row)

    def update_package(self, package, value, attribute = 'status'):
        '''
        Updates the <attribute> of the <package> with <value>

        Space-time complexity = O(1)
        '''

        if attribute == 'address':
            package.address = value
        elif attribute == 'deadline':
            package.deadline = value
        elif attribute == 'city':
            package.city = value
        elif attribute == 'zip':
            package.zip_code = value
        elif attribute == 'weight':
            package.weight = value
        elif attribute == 'status':
            package.status = value
    
    def pack9(self):
        '''
        Corrects the address for package #9.  

        Space-time complexity = O(1)
        '''

        # Pulls package from hash table
        nine = self.retrieve(9)
        
        # Updates attributes
        nine.address_id = 19
        nine.address = '410 S State St'
        nine.city = 'Salt Lake City'
        nine.zip = '84111'

        # Insets package back into hash table
        self.insert_package(nine)

    def lookup_packages(self, attribute, value):
        '''
        Returns a list of all packages that match the <value> of a given <attribute>. 
        Removes whitespace from <value> and converts to lowercase for reliable searching.
        All attributes support fuzzy searching with the exception of weight, where '1' == '12' would make no sense.  

        Space-time complexity = O(N^2) 
        '''

        # Initializes list used to return packages
        found = []

        # Removes leading/trailing whitespace and converts to lowercase for more reliable matching
        value = value.strip().lower()

        # Checks each package in hash table...
        for p in self.package_table:

            # Attributes = address
            if type(p) == Package and attribute == 'address' and value in p.address.strip().lower():
                found.append(p)

            # Attributes = deadline
            elif type(p) == Package and attribute == 'deadline' and value in p.deadline.strip().lower():
                found.append(p)

            # Attributes = city
            elif type(p) == Package and attribute == 'city' and value in p.city.strip().lower():
                found.append(p)

            # Attributes = zip
            elif type(p) == Package and attribute == 'zip_code' and value in p.zip_code.strip().lower():
                found.append(p)

            # Attributes = weight    
            elif type(p) == Package and attribute == 'weight' and p.weight.strip().lower() == value:
                found.append(p)

            # Attributes = status
            elif type(p) == Package and attribute == 'status' and value in p.status.strip().lower():
                found.append(p)
        
        # Returns list of all packages found in search above
        return found

    
