# Josh Gaweda Student ID #: 001150997

from datetime import datetime, timedelta
from HashTable import HashTable
from Package import Package 
from Truck import Truck
import re
import os 
clear = lambda: os.system('clear') & os.system('cls')

# Creates the global variables for this project (Hash Table, Time, Trucks)
receiveing = HashTable()
global_time = datetime(2021,1,10,8,00)
truck1 = Truck(receiveing.init1(), datetime(2021,1,10,9,5), 1, receiveing)
truck2 = Truck(receiveing.init2(), datetime(2021,1,10,8,00), 2, receiveing)
truck3 = Truck(receiveing.init3(), datetime(2021,1,10,23,59), 3, receiveing)

def start_deliveries(delivery_time = datetime(2021,1,10,23,59)):
    # Space-time complexity = O(N)
    # Runs all deliveries until global time matches the delivery time.  
    # This function will start each truck at the appropriate time, including truck 3 which will leave when another comes back.
    # Stops running deliveries once every package is delivered and all trucks returned to hub.  

    global global_time
    while global_time < delivery_time:

        # Starts truck 3
        if truck3.status == 'AT HUB, START OF DAY' and truck2.status == 'Deliveries complete' or truck1.status == 'Deliveries complete':
            truck3.time = global_time

        # Update package 9 at 10:20 AM
        if global_time == datetime(2021,1,10,10,20):
            receiveing.pack9()

        # If a truck has more deliveries to make, move the truck 0.1 miles & 20 seconds   
        # If there is no more miles to drive for that truck time does not increment 
        if global_time == truck1.time:
            truck1.move()
        if global_time == truck2.time:
            truck2.move()
        if global_time == truck3.time:
            truck3.move()

        # Deliveries completed
        if truck1.status == 'Deliveries complete' and truck2.status == 'Deliveries complete' and truck3.status == 'Deliveries complete':
            break

        # Increment global time 20 seconds
        global_time  += timedelta(0, 20)

    # Sets Global time to equal deliver time, 
    global_time = delivery_time

def time_of_day_prompt():
    # Prompts user for the time of day to check truck status against.  
    # If nothing is is entered, or an invalid time is entered, then will default to EOD.  
    # Space-time complexity = O(N)

    input_time = input("Enter a time in 24 hour format [hh:mm]\nOr press <enter> to set time to EOD - ")
    match = re.match(r'(\d+)\D+(\d+)', input_time)

    if match and match.lastindex == 2:
        hour = int(match.group(1))
        minute = int(match.group(2))
        start_deliveries(datetime(2021,1,10,hour,minute))
    else:
        start_deliveries()

def print_status():
    # Space-time complexity = O(1)
    # Prints hash table and truck status.
    clear
    print(receiveing)
    print(truck1)
    print(truck2)
    print(truck3)
    print(f'\nTotal miles driven = {round(truck1.miles_traveled + truck2.miles_traveled + truck3.miles_traveled, 1)}')
    print(f'Current time = {global_time.time()}')
    input('\nPress enter to continue...')

def print_package():
    packageId = input('Enter the package ID: ')
    package = receiveing.retrieve(int(packageId))
    package = str(package.__init__)
    id = re.search("package_id=(.+?),", package).group(1)
    address = re.search(", address='(.+?)',", package).group(1)
    city = re.search(", city='(.+?)',", package).group(1)
    zip_code = re.search(" zip_code='(.+?)',", package).group(1)
    deadline = re.search(" deadline='(.+?)',", package).group(1)
    status = re.search(", status='(.+?)',", package).group(1)
    try:
        instructions = re.search(", instructions='(.+?)'", package).group(1)
    except AttributeError:
        instructions = '-' 
    
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
    return_string += f'{id:^10}|{address:^42}|{city:^20}|{zip_code:^10}|{deadline:^10}|{status:^35}| {instructions:^20}\n'
    print(return_string)            
    input()


def main():
    # Main program method that controls interface.  
    # Prompts user to select an aoption
    while True:
        
        print(f'''---------------------------------------------------------------------------------------------------------------------------------------------------------------\n 
        Current time = {global_time.hour}:{global_time.minute:>02}

        1) Set time of day
        2) Print current package & truck status
        3) Lookup package by ID
        0) Exit program
        ''')

        selection = input('Please select an option: ').strip()

        # 1) Set time
        if selection == '1':
            time_of_day_prompt()
        
        # 2) Print current package & truck status(ALL)
        elif selection == '2':
            print_status()

        # 3) Lookup package based on ID
        elif selection == '3':
            print_package()

        # 0) Exit program
        elif selection == '0':
            exit()

        # Invalid input
        else:
            input('Invalid input, please try again')

if __name__ == "__main__":
    main()